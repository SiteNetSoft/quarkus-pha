package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Panel component ({@code pf-v6-c-panel}).
 *
 * <pre>
 * Panel.of("panel-basic").body("Main body content.").build()
 * Panel.of("p").bordered().header("&lt;strong&gt;Header&lt;/strong&gt;").body("...").footer("...").build()
 * Panel.of("p").scrollable().bordered().maxHeight("16rem").body("...").build()
 * Panel.of("p").scrollableAutoHeight().focusableMain().style("height: 100%").body("...").build()
 * </pre>
 *
 * <p>Fixed section order: header, main, footer. {@code body(html)} wraps the
 * main content in {@code __main-body}; {@code main(html)} renders it as a
 * direct child of {@code __main} (the "no body" anatomy). Section content is
 * raw HTML.
 */
@TemplateData
public final class Panel {

    private final String id;
    private final boolean bordered;
    private final boolean raised;
    private final boolean secondary;
    private final boolean scrollable;
    private final boolean scrollableAutoHeight;
    private final boolean pill;
    private final String style;
    private final String headerHtml;
    private final String mainHtml;
    private final boolean wrapBody;
    private final String maxHeight;
    private final boolean mainFocusable;
    private final String footerHtml;

    private Panel(Builder b) {
        this.id = b.id;
        this.bordered = b.bordered;
        this.raised = b.raised;
        this.secondary = b.secondary;
        this.scrollable = b.scrollable;
        this.scrollableAutoHeight = b.scrollableAutoHeight;
        this.pill = b.pill;
        this.style = b.style;
        this.headerHtml = b.headerHtml;
        this.mainHtml = b.mainHtml;
        this.wrapBody = b.wrapBody;
        this.maxHeight = b.maxHeight;
        this.mainFocusable = b.mainFocusable;
        this.footerHtml = b.footerHtml;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    /** Modifier-class suffix, leading-space separated. */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (bordered) {
            sb.append(" pf-m-bordered");
        }
        if (raised) {
            sb.append(" pf-m-raised");
        }
        if (secondary) {
            sb.append(" pf-m-secondary");
        }
        if (scrollable || scrollableAutoHeight) {
            sb.append(" pf-m-scrollable");
        }
        if (scrollableAutoHeight) {
            sb.append(" pf-m-scrollable-auto-height");
        }
        if (pill) {
            sb.append(" pf-m-pill");
        }
        return sb.toString();
    }

    public String style() {
        return style;
    }

    public String headerHtml() {
        return headerHtml;
    }

    public String mainHtml() {
        return mainHtml;
    }

    public boolean isWrapBody() {
        return wrapBody;
    }

    public String maxHeight() {
        return maxHeight;
    }

    public boolean isMainFocusable() {
        return mainFocusable;
    }

    public String footerHtml() {
        return footerHtml;
    }

    public static final class Builder {
        private String id;
        private boolean bordered;
        private boolean raised;
        private boolean secondary;
        private boolean scrollable;
        private boolean scrollableAutoHeight;
        private boolean pill;
        private String style;
        private String headerHtml;
        private String mainHtml;
        private boolean wrapBody;
        private String maxHeight;
        private boolean mainFocusable;
        private String footerHtml;

        private Builder() {
        }

        public Builder bordered() {
            this.bordered = true;
            return this;
        }

        public Builder raised() {
            this.raised = true;
            return this;
        }

        public Builder secondary() {
            this.secondary = true;
            return this;
        }

        public Builder scrollable() {
            this.scrollable = true;
            return this;
        }

        /** Scroll within whatever height the container gives (implies pf-m-scrollable). */
        public Builder scrollableAutoHeight() {
            this.scrollableAutoHeight = true;
            return this;
        }

        public Builder pill() {
            this.pill = true;
            return this;
        }

        /** Inline style on the panel root (e.g. "max-width: 400px", "height: 100%"). */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        /** Header section content (raw HTML). */
        public Builder header(String html) {
            this.headerHtml = html;
            return this;
        }

        /** Main content wrapped in {@code __main-body} (raw HTML). */
        public Builder body(String html) {
            this.mainHtml = html;
            this.wrapBody = true;
            return this;
        }

        /** Main content as a direct child of {@code __main} — no body wrapper (raw HTML). */
        public Builder main(String html) {
            this.mainHtml = html;
            this.wrapBody = false;
            return this;
        }

        /** Max height on {@code __main} (pair with {@link #scrollable()}). */
        public Builder maxHeight(String maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        /** tabindex="0" on {@code __main} so the scroll region is keyboard-reachable. */
        public Builder focusableMain() {
            this.mainFocusable = true;
            return this;
        }

        /** Footer section content (raw HTML). */
        public Builder footer(String html) {
            this.footerHtml = html;
            return this;
        }

        public Panel build() {
            if (mainHtml == null) {
                throw new IllegalStateException("A panel needs main content (body(...) or main(...))");
            }
            return new Panel(this);
        }
    }
}
