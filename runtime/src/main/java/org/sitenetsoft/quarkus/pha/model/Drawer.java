package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Drawer component ({@code pf-v6-c-drawer}).
 *
 * <pre>
 * Drawer.of("dr-basic").style("min-height: 240px")
 *     .toggleTextSwap("Open drawer", "Close drawer")
 *     .contentText("Main page content…")
 *     .panelSecondary().head("&lt;h2&gt;Panel title&lt;/h2&gt;")
 *     .panelBody("&lt;p&gt;Drawer panel content…&lt;/p&gt;", true, false).build()
 * Drawer.of("dr-resize").resizable(500, 150).toggle("Toggle drawer")
 *     .contentText("Drag the splitter…").head("&lt;span&gt;Drawer panel header&lt;/span&gt;").build()
 * Drawer.of("dr-static").staticMode().contentText("&lt;p&gt;pf-m-static…&lt;/p&gt;")
 *     .panelSecondary().panelBody("&lt;p&gt;Static panel…&lt;/p&gt;", true, false).build()
 * </pre>
 *
 * <p>The root owns the {@code expanded} Alpine state (or the phaDrawerResizable
 * factory). Toggle variants: {@code toggleTextSwap} (label follows state),
 * {@code toggle} (static label), {@code toggleFocus} (focuses the head's
 * x-ref on open). Head content is raw HTML — Alpine attribute text included.
 * Scope-sharing chrome compositions stay hand-written.
 */
@TemplateData
public final class Drawer {

    /** One drawer body region. */
    @TemplateData
    public static final class Body {
        private final String html;
        private final boolean padding;
        private final boolean noPadding;

        private Body(String html, boolean padding, boolean noPadding) {
            this.html = html;
            this.padding = padding;
            this.noPadding = noPadding;
        }

        public String html() {
            return html;
        }

        public boolean isPadding() {
            return padding;
        }

        public boolean isNoPadding() {
            return noPadding;
        }
    }

    private final String id;
    private final boolean inline;
    private final boolean staticMode;
    private final boolean pill;
    private final boolean panelLeft;
    private final boolean panelBottom;
    private final boolean startExpanded;
    private final Integer resizeDefault;
    private final Integer resizeMin;
    private final String style;
    private final String sectionHtml;
    private final String toggleOpenText;
    private final String toggleCloseText;
    private final String toggleText;
    private final boolean toggleFocus;
    private final String contentText;
    private final boolean firstBodyBare;
    private final List<Body> extraContentBodies;
    private final boolean panelSecondary;
    private final String panelWidths;
    private final String headHtml;
    private final List<Body> panelBodies;

    private Drawer(Builder b) {
        this.id = b.id;
        this.inline = b.inline;
        this.staticMode = b.staticMode;
        this.pill = b.pill;
        this.panelLeft = b.panelLeft;
        this.panelBottom = b.panelBottom;
        this.startExpanded = b.startExpanded;
        this.resizeDefault = b.resizeDefault;
        this.resizeMin = b.resizeMin;
        this.style = b.style;
        this.sectionHtml = b.sectionHtml;
        this.toggleOpenText = b.toggleOpenText;
        this.toggleCloseText = b.toggleCloseText;
        this.toggleText = b.toggleText;
        this.toggleFocus = b.toggleFocus;
        this.contentText = b.contentText;
        this.firstBodyBare = b.firstBodyBare;
        this.extraContentBodies = List.copyOf(b.extraContentBodies);
        this.panelSecondary = b.panelSecondary;
        this.panelWidths = b.panelWidths;
        this.headHtml = b.headHtml;
        this.panelBodies = List.copyOf(b.panelBodies);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    /** Root modifier-class suffix, leading-space separated (shell order). */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (inline) {
            sb.append(" pf-m-inline");
        }
        if (staticMode) {
            sb.append(" pf-m-static");
        }
        if (pill) {
            sb.append(" pf-m-pill");
        }
        if (panelLeft) {
            sb.append(" pf-m-panel-left");
        }
        if (panelBottom) {
            sb.append(" pf-m-panel-bottom");
        }
        return sb.toString();
    }

    public boolean isResizable() {
        return resizeDefault != null;
    }

    public String resizePosition() {
        if (panelLeft) {
            return "start";
        }
        if (panelBottom) {
            return "bottom";
        }
        return "end";
    }

    public String resizeDefaultAttr() {
        return resizeDefault != null ? String.valueOf(resizeDefault) : null;
    }

    public String resizeMinAttr() {
        return resizeMin != null ? String.valueOf(resizeMin) : null;
    }

    public boolean isStartOpen() {
        return staticMode || startExpanded;
    }

    public boolean isPanelBottom() {
        return panelBottom;
    }

    public String style() {
        return style;
    }

    public String sectionHtml() {
        return sectionHtml;
    }

    public boolean isHasToggle() {
        return toggleOpenText != null || toggleText != null;
    }

    public boolean isTextSwap() {
        return toggleOpenText != null;
    }

    public String toggleOpenText() {
        return toggleOpenText;
    }

    public String toggleCloseText() {
        return toggleCloseText;
    }

    public String toggleText() {
        return toggleText;
    }

    public boolean isToggleFocus() {
        return toggleFocus;
    }

    public String contentText() {
        return contentText;
    }

    public boolean isFirstBodyBare() {
        return firstBodyBare;
    }

    public List<Body> extraContentBodies() {
        return extraContentBodies;
    }

    public boolean isPanelSecondary() {
        return panelSecondary;
    }

    public String panelWidths() {
        return panelWidths;
    }

    public String headHtml() {
        return headHtml;
    }

    public List<Body> panelBodies() {
        return panelBodies;
    }

    public static final class Builder {
        private String id;
        private boolean inline;
        private boolean staticMode;
        private boolean pill;
        private boolean panelLeft;
        private boolean panelBottom;
        private boolean startExpanded;
        private Integer resizeDefault;
        private Integer resizeMin;
        private String style;
        private String sectionHtml;
        private String toggleOpenText;
        private String toggleCloseText;
        private String toggleText;
        private boolean toggleFocus;
        private String contentText;
        private boolean firstBodyBare;
        private final List<Body> extraContentBodies = new ArrayList<>();
        private boolean panelSecondary;
        private String panelWidths;
        private String headHtml;
        private final List<Body> panelBodies = new ArrayList<>();

        private Builder() {
        }

        public Builder inline() {
            this.inline = true;
            return this;
        }

        /** Always-expanded split pane (pf-m-static). */
        public Builder staticMode() {
            this.staticMode = true;
            return this;
        }

        /** Detached rounded panel (pf-m-pill). */
        public Builder pill() {
            this.pill = true;
            return this;
        }

        public Builder panelLeft() {
            this.panelLeft = true;
            return this;
        }

        public Builder panelBottom() {
            this.panelBottom = true;
            return this;
        }

        /** Start open without pf-m-static. */
        public Builder startExpanded() {
            this.startExpanded = true;
            return this;
        }

        /** phaDrawerResizable factory with default/min sizes (px). */
        public Builder resizable(int defaultSize, int minSize) {
            this.resizeDefault = defaultSize;
            this.resizeMin = minSize;
            return this;
        }

        /** Inline style on the root (demo sizing). */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        /** __section content (raw HTML) above the main area. */
        public Builder section(String sectionHtml) {
            this.sectionHtml = sectionHtml;
            return this;
        }

        /** Toggle whose label follows the state (x-text swap). */
        public Builder toggleTextSwap(String openText, String closeText) {
            this.toggleOpenText = Objects.requireNonNull(openText, "openText");
            this.toggleCloseText = Objects.requireNonNull(closeText, "closeText");
            return this;
        }

        /** Static-label toggle. */
        public Builder toggle(String text) {
            this.toggleText = Objects.requireNonNull(text, "text");
            return this;
        }

        /** Static-label toggle that focuses the head's x-ref on open. */
        public Builder toggleFocus(String text) {
            this.toggleText = Objects.requireNonNull(text, "text");
            this.toggleFocus = true;
            return this;
        }

        /** Raw HTML after the toggle in the first content body (or alone). */
        public Builder contentText(String contentText) {
            this.contentText = contentText;
            return this;
        }

        /** First content body without pf-m-padding (stacked pattern). */
        public Builder firstBodyBare() {
            this.firstBodyBare = true;
            return this;
        }

        /** Additional content body. */
        public Builder contentBody(String html, boolean padding, boolean noPadding) {
            extraContentBodies.add(new Body(Objects.requireNonNull(html, "html"), padding, noPadding));
            return this;
        }

        public Builder panelSecondary() {
            this.panelSecondary = true;
            return this;
        }

        /** Verbatim width classes, e.g. "pf-m-width-33 pf-m-width-50-on-lg". */
        public Builder panelWidths(String panelWidths) {
            this.panelWidths = panelWidths;
            return this;
        }

        /** Panel head content (raw HTML — Alpine attribute text allowed). */
        public Builder head(String headHtml) {
            this.headHtml = headHtml;
            return this;
        }

        /** Panel body. */
        public Builder panelBody(String html, boolean padding, boolean noPadding) {
            panelBodies.add(new Body(Objects.requireNonNull(html, "html"), padding, noPadding));
            return this;
        }

        public Drawer build() {
            return new Drawer(this);
        }
    }
}
