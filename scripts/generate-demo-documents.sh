#!/bin/bash
set -euo pipefail

# Generate demo documents for the document-editor showcase using
# LibreOffice headless on the host.
#
# Output: integration-tests/src/main/resources/documents/
#   welcome.odt    — text document
#   budget.ods     — spreadsheet
#   slides.odp     — presentation
#   policy.pdf     — PDF rendered from welcome.odt

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
OUT_DIR="$PROJECT_ROOT/integration-tests/src/main/resources/documents"
WORK_DIR="$(mktemp -d)"
trap 'rm -rf "$WORK_DIR"' EXIT

command -v libreoffice >/dev/null 2>&1 || {
  echo "libreoffice not found on PATH" >&2
  exit 1
}

mkdir -p "$OUT_DIR"

echo "==> Building source files in $WORK_DIR..."

cat > "$WORK_DIR/welcome.html" <<'EOF'
<!doctype html>
<html><head><meta charset="utf-8"><title>Welcome</title></head>
<body>
<h1>Welcome to quarkus-pha</h1>
<p>This is a sample text document used by the <strong>document-editor</strong>
showcase. It is served via the WOPI host endpoint and rendered inside
Collabora Online (LibreOffice in the browser).</p>

<h2>What you can do</h2>
<ul>
  <li>Edit text and formatting from the browser</li>
  <li>Use the toolbar to save, print, download, and go fullscreen</li>
  <li>Multiple users can edit the same document collaboratively</li>
</ul>

<h2>About the stack</h2>
<p>The browser embeds the editor via an <code>iframe</code>, and communicates
with the server using the <em>WOPI protocol</em> and PostMessage API. Quarkus
serves the document bytes; Collabora Online runs the actual office engine.</p>
</body></html>
EOF

cat > "$WORK_DIR/budget.csv" <<'EOF'
Month,Revenue,Expenses,Net
January,12500,8400,4100
February,13900,9100,4800
March,15200,9700,5500
April,14100,8800,5300
May,16300,10200,6100
June,17500,10900,6600
EOF

# Flat ODP source for a presentation
cat > "$WORK_DIR/slides.fodp" <<'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<office:document xmlns:office="urn:oasis:names:tc:opendocument:xmlns:office:1.0"
                 xmlns:style="urn:oasis:names:tc:opendocument:xmlns:style:1.0"
                 xmlns:text="urn:oasis:names:tc:opendocument:xmlns:text:1.0"
                 xmlns:draw="urn:oasis:names:tc:opendocument:xmlns:drawing:1.0"
                 xmlns:svg="urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0"
                 xmlns:presentation="urn:oasis:names:tc:opendocument:xmlns:presentation:1.0"
                 xmlns:fo="urn:oasis:names:tc:opendocument:xmlns:xsl-fo-compatible:1.0"
                 office:version="1.3"
                 office:mimetype="application/vnd.oasis.opendocument.presentation">
  <office:automatic-styles>
    <style:style style:name="title" style:family="presentation">
      <style:graphic-properties draw:stroke="none" draw:fill="none"/>
      <style:paragraph-properties fo:text-align="center"/>
      <style:text-properties fo:font-size="36pt" fo:font-weight="bold"/>
    </style:style>
    <style:style style:name="body" style:family="presentation">
      <style:graphic-properties draw:stroke="none" draw:fill="none"/>
      <style:text-properties fo:font-size="22pt"/>
    </style:style>
  </office:automatic-styles>
  <office:body>
    <office:presentation>
      <draw:page draw:name="Cover" draw:style-name="dp1" draw:master-page-name="Default">
        <draw:frame presentation:style-name="title" draw:layer="layout"
                    svg:width="22cm" svg:height="4cm" svg:x="2cm" svg:y="6cm"
                    presentation:class="title">
          <draw:text-box><text:p>quarkus-pha</text:p></draw:text-box>
        </draw:frame>
        <draw:frame presentation:style-name="body" draw:layer="layout"
                    svg:width="22cm" svg:height="3cm" svg:x="2cm" svg:y="11cm">
          <draw:text-box><text:p>Framework-free frontend components on Quarkus</text:p></draw:text-box>
        </draw:frame>
      </draw:page>
      <draw:page draw:name="Stack" draw:style-name="dp1" draw:master-page-name="Default">
        <draw:frame presentation:style-name="title" draw:layer="layout"
                    svg:width="22cm" svg:height="3cm" svg:x="2cm" svg:y="2cm"
                    presentation:class="title">
          <draw:text-box><text:p>The stack</text:p></draw:text-box>
        </draw:frame>
        <draw:frame presentation:style-name="body" draw:layer="layout"
                    svg:width="22cm" svg:height="10cm" svg:x="2cm" svg:y="6cm">
          <draw:text-box>
            <text:p>PatternFly — design system (CSS only)</text:p>
            <text:p>Alpine.js — reactivity</text:p>
            <text:p>HTMX — partial swaps from the server</text:p>
            <text:p>Qute — server-side templating</text:p>
          </draw:text-box>
        </draw:frame>
      </draw:page>
    </office:presentation>
  </office:body>
</office:document>
EOF

echo "==> Converting with libreoffice..."

run_lo() {
  libreoffice --headless \
    -env:UserInstallation="file://$WORK_DIR/lo-profile" \
    "$@" >/dev/null
}

run_lo --convert-to odt --outdir "$WORK_DIR" "$WORK_DIR/welcome.html"
run_lo --convert-to ods --infilter=CSV --outdir "$WORK_DIR" "$WORK_DIR/budget.csv"
run_lo --convert-to odp --outdir "$WORK_DIR" "$WORK_DIR/slides.fodp"
run_lo --convert-to pdf --outdir "$WORK_DIR" "$WORK_DIR/welcome.html"

mv "$WORK_DIR/welcome.odt"  "$OUT_DIR/welcome.odt"
mv "$WORK_DIR/budget.ods"   "$OUT_DIR/budget.ods"
mv "$WORK_DIR/slides.odp"   "$OUT_DIR/slides.odp"
mv "$WORK_DIR/welcome.pdf"  "$OUT_DIR/policy.pdf"

echo ""
echo "==> Demo documents written to $OUT_DIR:"
ls -la "$OUT_DIR"
