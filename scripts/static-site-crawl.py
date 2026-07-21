#!/usr/bin/env python3
"""Export a static snapshot of the quarkus-pha demo site for GitHub Pages.

Crawls a RUNNING showcase server (start it with scripts/e2e.sh start), writes
a fully static mirror, rewrites absolute URLs to a base path, pre-renders
parameterless GET /api/htmx/* fragments (those demos keep working), badges
server-driven examples as inert, and hard-fails if any internal reference
does not resolve to an exported file.

Stdlib only. See scripts/static-site.sh for the one-command wrapper.
"""
import argparse
import os
import re
import sys
import urllib.error
import urllib.parse
import urllib.request
from collections import deque

HTML_EXTS = ('.html', '.htm')
FILE_EXT_RE = re.compile(r'\.[a-z0-9]{1,8}$', re.I)
ATTR_URL_RE = re.compile(
    r'(\b(?:href|src|poster|action|hx-get|hx-post|hx-put|hx-delete|sse-connect|'
    r'data-source-url|data-source-java-url|data-src|data-persist-url|'
    r'data-java-href|data-rendered-href|data-source-href)\s*=\s*)(["\'])(/[^"\']*)\2',
    re.I)
SRCSET_RE = re.compile(r'(\bsrcset\s*=\s*)(["\'])([^"\']+)\2', re.I)
CSS_URL_RE = re.compile(r'url\(\s*(["\']?)(/[^)"\']+)\1\s*\)')
DYN_SCAN_RE = re.compile(
    r'["\'](/(?:components/[a-z0-9-]+/source(?:-java)?/[a-z0-9-]+|api/htmx/[a-z0-9/_-]+))[?"\']?')
# Documentation prose that shows raw attribute syntax as text to read (e.g.
# `<code class="ws-code">hx-post="/switch-context"</code>`), not a live
# element. Must never be scanned for crawl targets or have its text rewritten
# with the base path. NOTE: Prettier line-wraps some closing tags as
# `</code\n  >` — `\s*` between "code" and ">" is required, or the
# non-greedy `.*?` overruns past the real close hunting for a literal
# `</code>` and swallows real markup (and its links) into the "prose" span.
CODE_SAMPLE_RE = re.compile(r'<code\b[^>]*\bws-code\b[^>]*>.*?</code\s*>', re.S | re.I)
SERVER_MARKER_RE = re.compile(
    r'hx-post|hx-put|hx-delete|hx-get\s*=\s*["\'][^"\']*\?|sse-connect|hx-ext\s*=\s*["\']sse|'
    r'document-editor|data-persist-url|hx-trigger\s*=\s*["\'][^"\']*(?:keyup|keydown|input)',
    re.I)
EXAMPLE_OPEN_RE = re.compile(r'<div\b[^>]*class\s*=\s*["\'][^"\']*ws-preview-html')

BADGE_HTML = ('<div class="pha-static-badge" role="note">Live demo — this example is '
              'server-driven; run the showcase locally for the interactive version.</div>')
FOOTER_HTML = ('<div class="pha-static-footer">Static snapshot of the quarkus-pha showcase '
               '&mdash; <a href="https://github.com/SiteNetSoft/quarkus-pha">source &amp; live '
               'version</a></div>')
STATIC_CSS = """/* Injected by static-site-crawl.py — static-build chrome only. */
.pha-static-badge {
  display: inline-block;
  margin-block-end: 0.5rem;
  padding: 0.25rem 0.75rem;
  border-radius: 999px;
  font-size: 0.75rem;
  background: var(--pf-t--global--color--nonstatus--yellow--default, #ffe8cc);
  color: var(--pf-t--global--text--color--regular, #151515);
}
.pha-static-footer {
  padding: 1rem;
  text-align: center;
  font-size: 0.85rem;
  color: var(--pf-t--global--text--color--subtle, #4d4d4d);
}
"""


def log(msg):
    print(msg, flush=True)


class Exporter:
    def __init__(self, base_url, base_path, out):
        self.base_url = base_url.rstrip('/')
        self.bp = '/' + base_path.strip('/') + '/'
        self.out = out
        self.queue = deque(['/'])
        self.seen = {'/'}
        self.pages = {}          # url path -> html text (pre-rewrite)
        self.assets = 0
        self.fragments = {}      # /api/htmx/... -> saved relative file (with .html)
        self.fragment_raw = {}   # /api/htmx/... -> raw fetched text (pre-rewrite)
        self.css_raw = {}        # relative out path -> raw fetched CSS text (pre-rewrite)
        self.dead_endpoints = set()   # /api/htmx/... fetch failures (reported count)
        self.dead_pages = set()       # any path whose GET failed (selfcheck exemption)
        self.sources = 0
        self.script_report = []

    # ---- fetch/save -------------------------------------------------------
    def fetch(self, path):
        req = urllib.request.Request(self.base_url + path, headers={'HX-Request': 'true'}
                                     if path.startswith('/api/htmx/') else {})
        with urllib.request.urlopen(req, timeout=30) as r:
            return r.read(), r.headers.get('Content-Type', '')

    def dest_for(self, path):
        p = path.lstrip('/')
        if path.startswith('/api/htmx/'):
            return p + '.html'
        if '/source/' in path or '/source-java/' in path:
            return p
        if FILE_EXT_RE.search(path):
            return p
        return (p + '/' if p else '') + 'index.html'

    def save(self, rel, data):
        fp = os.path.join(self.out, rel)
        os.makedirs(os.path.dirname(fp) or self.out, exist_ok=True)
        with open(fp, 'wb') as f:
            f.write(data)

    # ---- crawl ------------------------------------------------------------
    def enqueue(self, path):
        if path.startswith('//'):
            # Protocol-relative absolute URL (e.g. "//github.com/..."), not
            # a site-internal path. Must be rejected *before* urlparse: it
            # would otherwise treat "github.com" as the netloc and hand back
            # a deceptive single-leading-slash .path ("/SiteNetSoft/...").
            return
        path = urllib.parse.urlparse(path).path
        if path and path.startswith('/') and path not in self.seen:
            self.seen.add(path)
            self.queue.append(path)

    @staticmethod
    def _mask_code_samples(text):
        # Blank out <code class="ws-code">...</code> doc-prose spans (same
        # length, so this is only ever used for scanning, never for output)
        # so their literal attribute-syntax text is never mistaken for a
        # live link to crawl.
        return CODE_SAMPLE_RE.sub(lambda m: ' ' * len(m.group(0)), text)

    def _scan_links(self, text):
        # Discovery uses the same attribute set as rewrite_html (ATTR_URL_RE,
        # SRCSET_RE, CSS_URL_RE) plus DYN_SCAN_RE, so nothing that gets
        # rewritten to a base-path URL is left un-crawled (e.g. a <video
        # poster> or an inline style="...url(...)" background image).
        masked = self._mask_code_samples(text)
        for m in ATTR_URL_RE.finditer(masked):
            self.enqueue(m.group(3))
        for m in SRCSET_RE.finditer(masked):
            for part in m.group(3).split(','):
                u = part.strip().split()[0] if part.strip() else ''
                if u.startswith('/'):
                    self.enqueue(u)
        for m in CSS_URL_RE.finditer(masked):
            self.enqueue(m.group(2))
        for m in DYN_SCAN_RE.finditer(masked):
            self.enqueue(m.group(1))

    def crawl(self):
        while self.queue:
            path = self.queue.popleft()
            try:
                data, ctype = self.fetch(path)
            except (urllib.error.HTTPError, urllib.error.URLError) as e:
                self.dead_pages.add(path)
                if path.startswith('/api/htmx/'):
                    self.dead_endpoints.add(path)
                    continue
                log(f'  WARN {path}: {e}')
                continue
            if path.startswith('/api/htmx/'):
                # Fragments are HTML too, and may themselves link to further
                # fragments (e.g. a wizard step fragment linking to the next
                # step) that would otherwise never get enqueued. Scan them
                # for links just like pages, but defer rewriting+saving to
                # finalize() once self.fragments is fully populated (a
                # fragment referenced by another fragment fetched earlier in
                # the queue must still resolve to its saved .html filename).
                text = data.decode('utf-8')
                self.fragments[path] = self.dest_for(path)
                self.fragment_raw[path] = text
                self._scan_links(text)
            elif 'text/html' in ctype and '/source' not in path:
                text = data.decode('utf-8')
                self.pages[path] = text
                self._scan_links(text)
            else:
                rel = self.dest_for(path)
                if '/source/' in path or '/source-java/' in path:
                    self.sources += 1
                    self.save(rel, data)
                elif rel.endswith('.css'):
                    # Deferred to finalize() for the same reason as fragments.
                    text = data.decode('utf-8')
                    self.css_raw[rel] = text
                    for m in CSS_URL_RE.finditer(text):
                        self.enqueue(m.group(2))
                    self.assets += 1
                else:
                    self.assets += 1
                    self.save(rel, data)
        if not self.pages:
            log('ERROR: crawl produced 0 pages (server unreachable or routes broken?)')
            sys.exit(1)
        log(f'  crawl: {len(self.pages)} pages, {self.assets} assets, '
            f'{len(self.fragments)} fragments, {self.sources} source files, '
            f'{len(self.dead_endpoints)} inert endpoints')

    # ---- rewrite ----------------------------------------------------------
    def _rw_url(self, u):
        if u.startswith('//'):
            # Protocol-relative URL (e.g. "//github.com/...") — an external
            # absolute reference, not a site-relative path. Leave untouched.
            return u
        if u in self.fragments:
            return self.bp + self.fragments[u]
        return self.bp + u[1:]

    def _rewrite_chunk(self, text):
        def attr(m):
            return m.group(1) + m.group(2) + self._rw_url(m.group(3)) + m.group(2)
        text = ATTR_URL_RE.sub(attr, text)

        def srcset(m):
            parts = [p.strip() for p in m.group(3).split(',')]
            parts = [(self._rw_url(p.split()[0]) + ' ' + ' '.join(p.split()[1:])).strip()
                     if p.startswith('/') else p for p in parts]
            return m.group(1) + m.group(2) + ', '.join(parts) + m.group(2)
        text = SRCSET_RE.sub(srcset, text)
        text = CSS_URL_RE.sub(lambda m: 'url({}{}{})'.format(m.group(1), self._rw_url(m.group(2)), m.group(1)), text)
        return text

    def rewrite_html(self, text):
        # <code class="ws-code"> doc-prose spans show raw attribute syntax as
        # text to read (e.g. hx-post="/switch-context") — never a live link.
        # Leave them byte-for-byte untouched; rewrite everything else.
        parts = []
        pos = 0
        for m in CODE_SAMPLE_RE.finditer(text):
            parts.append(self._rewrite_chunk(text[pos:m.start()]))
            parts.append(m.group(0))
            pos = m.end()
        parts.append(self._rewrite_chunk(text[pos:]))
        return ''.join(parts)

    def rewrite_css(self, text):
        return CSS_URL_RE.sub(lambda m: 'url({}{}{})'.format(m.group(1), self._rw_url(m.group(2)), m.group(1)), text)

    @staticmethod
    def _inject_page_badge(text):
        # Fallback for pages where a genuinely server-driven marker sits
        # outside any ws-preview-html wrapper (e.g. table sort, live-search,
        # drag-and-drop persist, an SSE log stream) — those demos render
        # their live element directly with no wrapper div to badge inside
        # of. Insert once, right after the page's <main> (or <body> if no
        # <main> is present) opening tag.
        m = re.search(r'<main\b[^>]*>', text, re.I | re.S)
        if not m:
            m = re.search(r'<body\b[^>]*>', text, re.I | re.S)
        if not m:
            return text
        pos = m.end()
        return text[:pos] + BADGE_HTML + text[pos:]

    def inject(self, text):
        # badge per server-driven example region (approximate region = chunk
        # between consecutive ws-preview-html openings; nesting inside a chunk
        # does not matter for "does this chunk contain a marker")
        opens = [m.start() for m in EXAMPLE_OPEN_RE.finditer(text)]
        chunk_ranges = []
        for i in range(len(opens)):
            start = opens[i]
            end = opens[i + 1] if i + 1 < len(opens) else len(text)
            chunk_ranges.append((start, end))

        # Determine, on the un-mutated text, whether any real (non-doc-prose)
        # server marker falls outside every ws-preview-html chunk. Masking
        # <code class="ws-code"> spans keeps this from firing on pages that
        # merely *describe* hx-post/etc. in prose without any live element.
        masked = self._mask_code_samples(text)
        marker_positions = [m.start() for m in SERVER_MARKER_RE.finditer(masked)]
        uncovered = any(
            not any(start <= p < end for start, end in chunk_ranges)
            for p in marker_positions)

        for i in reversed(range(len(chunk_ranges))):
            start, end = chunk_ranges[i]
            chunk = text[start:end]
            if SERVER_MARKER_RE.search(chunk):
                gt = text.index('>', start) + 1
                text = text[:gt] + BADGE_HTML + text[gt:]

        if uncovered:
            text = self._inject_page_badge(text)

        css_link = '<link rel="stylesheet" href="{}web/css/pha-static.css">'.format(self.bp)
        text = text.replace('</head>', css_link + '</head>', 1)
        text = text.replace('</body>', FOOTER_HTML + '</body>', 1)
        return text

    def finalize(self):
        # All URL rewriting happens here, after crawl() has fully populated
        # self.fragments, so cross-references between fragments (or from
        # fragments/CSS to fragments) resolve to the correct saved filename
        # instead of falling back to a path without the .html suffix.
        for path, text in self.fragment_raw.items():
            self.save(self.fragments[path], self.rewrite_html(text).encode('utf-8'))
        for rel, text in self.css_raw.items():
            self.save(rel, self.rewrite_css(text).encode('utf-8'))
        for path, text in self.pages.items():
            for m in re.finditer(r'<script\b[^>]*>(.*?)</script>', text, re.S):
                for u in re.findall(r'["\'](/(?:components|api|web)/[^"\']*)["\']', m.group(1)):
                    self.script_report.append(f'{path}: inline script URL {u}')
            self.save(self.dest_for(path), self.inject(self.rewrite_html(text)).encode('utf-8'))
        self.save('web/css/pha-static.css', STATIC_CSS.encode('utf-8'))
        self.save('.nojekyll', b'')
        try:
            data, _ = self.fetch('/pha-static-404-probe')
            body = data.decode('utf-8')
        except urllib.error.HTTPError as e:
            body = e.read().decode('utf-8', 'replace') if e.fp else ''
        body_lower = body.lower()
        if not ('<html' in body_lower and '<head' in body_lower):
            body = ('<!doctype html><meta charset="utf-8"><title>Not found</title>'
                    '<p>Page not found. <a href="{}">quarkus-pha documentation</a></p>'.format(self.bp))
        else:
            body = self.inject(self.rewrite_html(body))
        self.save('404.html', body.encode('utf-8'))
        if self.script_report:
            log('  script-URL audit (manual review; not rewritten):')
            for line in sorted(set(self.script_report)):
                log('    ' + line)

    # ---- self-check -------------------------------------------------------
    def _expected_export(self, tgt):
        """Exact exported filename (relative to self.out) an internal URL
        with base-path stripped ("tgt") must match. No guessing fallback —
        mirrors dest_for()'s own branching so the check and the writer agree.
        """
        if not tgt:
            return 'index.html'
        p = '/' + tgt
        if tgt.startswith('api/htmx/') or '/source/' in p or '/source-java/' in p:
            # Fragments already carry their saved-in-full name (via the
            # fragments map rewrite, or are an unresolved/dead endpoint
            # caught by the dead_pages check above); source files are
            # saved under their exact request name with no extension added.
            return tgt
        if FILE_EXT_RE.search(tgt):
            return tgt
        return (tgt if tgt.endswith('/') else tgt + '/') + 'index.html'

    def selfcheck(self):
        bad = []
        for root, _, files in os.walk(self.out):
            for fn in files:
                fp = os.path.join(root, fn)
                rel = os.path.relpath(fp, self.out)
                if fn.endswith(HTML_EXTS):
                    text = open(fp, encoding='utf-8').read()
                    refs = [m.group(3) for m in ATTR_URL_RE.finditer(text)]
                    refs += [m.group(2) for m in CSS_URL_RE.finditer(text)]
                    # Hardening: any attribute value that still points at a
                    # bare, un-prefixed /components/, /web/, or /api/ path is
                    # always a rewrite defect, independent of whether it
                    # happens to match anything crawled/saved (this is how
                    # the CODE_SAMPLE_RE mask-overrun regression — real links
                    # swallowed into a doc-prose span and left un-rewritten —
                    # went undetected: the old check only ever looked at refs
                    # that already started with the base path). Scanned
                    # outside <code class="ws-code"> spans, which
                    # intentionally keep bare paths as literal doc text.
                    masked = self._mask_code_samples(text)
                    for m in ATTR_URL_RE.finditer(masked):
                        v = m.group(3)
                        if v.startswith(('/components/', '/web/', '/api/')) and not v.startswith(self.bp):
                            bad.append(f'{rel} -> UNREWRITTEN {v}')
                elif fn.endswith('.css'):
                    refs = [m.group(2) for m in CSS_URL_RE.finditer(open(fp, encoding='utf-8').read())]
                else:
                    continue
                for u in refs:
                    if u.startswith('//') or not u.startswith(self.bp) or '?' in u:
                        continue
                    tgt = u[len(self.bp):].split('#')[0]
                    if ('/' + tgt).rstrip('/') in self.dead_pages:
                        continue
                    cand = self._expected_export(tgt)
                    if not os.path.exists(os.path.join(self.out, cand)):
                        bad.append(f'{rel} -> {u}')
        if bad:
            log('SELF-CHECK FAILED ({} broken refs):'.format(len(bad)))
            for line in sorted(set(bad))[:100]:
                log('  ' + line)
            return False
        log('  self-check: all internal references resolve')
        return True


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--base-url', default='http://localhost:9090')
    ap.add_argument('--base-path', default='/quarkus-pha/')
    ap.add_argument('--out', default='build/static-site')
    args = ap.parse_args()
    ex = Exporter(args.base_url, args.base_path, args.out)
    log('==> Crawling {} ...'.format(args.base_url))
    ex.crawl()
    log('==> Rewriting + injecting ...')
    ex.finalize()
    log('==> Self-check ...')
    ok = ex.selfcheck()
    log('==> Export {} at {}'.format('COMPLETE' if ok else 'FAILED', args.out))
    sys.exit(0 if ok else 1)


if __name__ == '__main__':
    main()
