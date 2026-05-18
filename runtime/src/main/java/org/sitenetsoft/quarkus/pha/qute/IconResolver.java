package org.sitenetsoft.quarkus.pha.qute;

import io.quarkus.qute.RawString;
import io.quarkus.qute.TemplateExtension;
import org.jboss.logging.Logger;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Server-side SVG icon resolver exposed as the {@code icons} Qute namespace.
 *
 * <p>Usage from a Qute template:
 * <pre>
 *   {icons:svg('fa:check').raw}
 *   {icons:svg('pficon:cluster', 'Cluster overview').raw}
 * </pre>
 *
 * <p>The {@code name} is {@code set:slug} where {@code set} is one of
 * {@code fa}, {@code fa-regular}, {@code fa-brands}, or {@code pficon}.
 *
 * <p>Icons are read lazily from the classpath under
 * {@code META-INF/resources/web/vendor/icons/&lt;dir&gt;/&lt;slug&gt;.svg}
 * (the vendor pipeline lays them out matching the {@code set} prefix), parsed
 * once to extract the {@code viewBox} and inner content, and cached forever.
 *
 * <p>Unknown names render a visible red placeholder marked with
 * {@code data-missing-icon="..."} and the resolver logs one {@code WARN} per
 * unique missing name. The smoke tests assert that the placeholder marker
 * never appears in a rendered route — a typo fails CI loudly.
 */
public final class IconResolver {

    private static final Logger LOG = Logger.getLogger(IconResolver.class);

    private static final Map<String, String> SET_DIRS = Map.of(
        "fa",         "fa-solid",
        "fa-regular", "fa-regular",
        "fa-brands",  "fa-brands",
        "pficon",     "pficon"
    );

    private static final String CLASSPATH_BASE = "META-INF/resources/web/vendor/icons/";

    /** Cached parsed result per icon name. {@code null} value means "tried and missing". */
    private static final ConcurrentHashMap<String, Parsed> CACHE = new ConcurrentHashMap<>();

    /** Names we've already logged as missing — keeps WARN output to once per name. */
    private static final Set<String> MISSING_LOGGED = ConcurrentHashMap.newKeySet();

    private static final Pattern VIEWBOX_RE = Pattern.compile("viewBox\\s*=\\s*\"([^\"]+)\"");
    private static final Pattern SVG_OPEN_RE  = Pattern.compile("<svg\\b[^>]*>", Pattern.DOTALL);
    private static final Pattern SVG_CLOSE_RE = Pattern.compile("</svg\\s*>", Pattern.DOTALL);
    private static final Pattern COMMENT_RE   = Pattern.compile("<!--.*?-->", Pattern.DOTALL);
    private static final Pattern XML_DECL_RE  = Pattern.compile("<\\?xml[^?]*\\?>", Pattern.DOTALL);

    private IconResolver() { }

    @TemplateExtension(namespace = "icons")
    public static RawString svg(String name) {
        return svg(name, null);
    }

    @TemplateExtension(namespace = "icons")
    public static RawString svg(String name, String label) {
        Parsed parsed = lookup(name);
        if (parsed == null) {
            return new RawString(renderMissing(name, label));
        }
        return new RawString(renderSvg(parsed, label));
    }

    /* ---- internals ---- */

    private static Parsed lookup(String name) {
        if (name == null) return null;

        /*
         * computeIfAbsent can't store nulls — use a sentinel to remember misses
         * so we don't re-read the classpath on every render for the same typo.
         */
        Parsed cached = CACHE.get(name);
        if (cached == Parsed.MISSING) return null;
        if (cached != null) return cached;

        Parsed loaded = load(name);
        CACHE.put(name, loaded == null ? Parsed.MISSING : loaded);
        if (loaded == null) {
            warnOnce(name);
        }
        return loaded;
    }

    private static Parsed load(String name) {
        int colon = name.indexOf(':');
        if (colon <= 0 || colon == name.length() - 1) return null;

        String set  = name.substring(0, colon);
        String slug = name.substring(colon + 1);
        String dir  = SET_DIRS.get(set);
        if (dir == null) return null;
        if (!isSafeSlug(slug)) return null;

        String resource = CLASSPATH_BASE + dir + "/" + slug + ".svg";
        String raw = readResource(resource);
        if (raw == null) return null;

        return parse(raw);
    }

    private static String readResource(String path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) cl = IconResolver.class.getClassLoader();
        try (InputStream in = cl.getResourceAsStream(path)) {
            if (in == null) return null;
            ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
            byte[] buf = new byte[2048];
            int n;
            while ((n = in.read(buf)) != -1) {
                out.write(buf, 0, n);
            }
            return out.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            LOG.warnf("Failed to read icon resource %s: %s", path, e.getMessage());
            return null;
        }
    }

    private static Parsed parse(String raw) {
        String cleaned = XML_DECL_RE.matcher(raw).replaceAll("");
        cleaned = COMMENT_RE.matcher(cleaned).replaceAll("");

        Matcher openMatcher = SVG_OPEN_RE.matcher(cleaned);
        if (!openMatcher.find()) return null;
        String openTag = openMatcher.group();
        int innerStart = openMatcher.end();

        Matcher closeMatcher = SVG_CLOSE_RE.matcher(cleaned);
        if (!closeMatcher.find(innerStart)) return null;
        int innerEnd = closeMatcher.start();

        String viewBox = "0 0 24 24";
        Matcher vbMatcher = VIEWBOX_RE.matcher(openTag);
        if (vbMatcher.find()) {
            viewBox = vbMatcher.group(1);
        }

        String inner = cleaned.substring(innerStart, innerEnd).trim();
        return new Parsed(viewBox, inner);
    }

    private static String renderSvg(Parsed parsed, String label) {
        StringBuilder sb = new StringBuilder(parsed.inner.length() + 192);
        sb.append("<svg xmlns=\"http://www.w3.org/2000/svg\"")
          .append(" width=\"1em\" height=\"1em\"")
          .append(" fill=\"currentColor\"")
          .append(" viewBox=\"").append(parsed.viewBox).append("\"");
        if (label != null && !label.isEmpty()) {
            sb.append(" role=\"img\" aria-label=\"").append(escapeAttr(label)).append("\"");
        } else {
            sb.append(" aria-hidden=\"true\" focusable=\"false\"");
        }
        sb.append(">").append(parsed.inner).append("</svg>");
        return sb.toString();
    }

    private static String renderMissing(String name, String label) {
        String displayName = name == null ? "" : name;
        String ariaLabel = (label != null && !label.isEmpty())
            ? label
            : "missing icon: " + displayName;
        return "<svg xmlns=\"http://www.w3.org/2000/svg\""
            + " width=\"1em\" height=\"1em\""
            + " viewBox=\"0 0 24 24\" fill=\"none\""
            + " stroke=\"#c9190b\" stroke-width=\"2\""
            + " role=\"img\""
            + " aria-label=\"" + escapeAttr(ariaLabel) + "\""
            + " data-missing-icon=\"" + escapeAttr(displayName) + "\">"
            + "<rect x=\"3\" y=\"3\" width=\"18\" height=\"18\" rx=\"2\" ry=\"2\"/>"
            + "<path d=\"M9 9a3 3 0 1 1 4.5 2.6c-1 .6-1.5 1.2-1.5 2.4\"/>"
            + "<circle cx=\"12\" cy=\"17\" r=\"1\" fill=\"#c9190b\" stroke=\"none\"/>"
            + "</svg>";
    }

    private static boolean isSafeSlug(String slug) {
        if (slug.isEmpty() || slug.length() > 80) return false;
        for (int i = 0; i < slug.length(); i++) {
            char c = slug.charAt(i);
            boolean ok = (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '-';
            if (!ok) return false;
        }
        return true;
    }

    private static String escapeAttr(String s) {
        if (s == null) return "";
        StringBuilder out = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '&':  out.append("&amp;");  break;
                case '<':  out.append("&lt;");   break;
                case '>':  out.append("&gt;");   break;
                case '"':  out.append("&quot;"); break;
                case '\'': out.append("&#39;");  break;
                default:   out.append(c);
            }
        }
        return out.toString();
    }

    private static void warnOnce(String name) {
        if (MISSING_LOGGED.add(name)) {
            LOG.warnf("icons:svg('%s') — unknown icon, rendering placeholder", name);
        }
    }

    private static final class Parsed {
        static final Parsed MISSING = new Parsed("", "");
        final String viewBox;
        final String inner;
        Parsed(String viewBox, String inner) {
            this.viewBox = viewBox;
            this.inner = inner;
        }
    }
}
