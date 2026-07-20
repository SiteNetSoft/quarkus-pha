package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Sidebar component ({@code pf-v6-c-sidebar}).
 *
 * <pre>
 * Sidebar.of("sb").panel("Filters…").content("Main content…").build()
 * Sidebar.of("sb").split().panelSecondary().panelPadding()
 *     .panel("…").contentPadding().content("…").build()
 * Sidebar.of("sb").panelRight().gutter().contentFirst()
 *     .panelStyle("…").panel("…").contentStyle("padding: 1rem").content("…").build()
 * Sidebar.of("sb").panelSticky().panelSecondary().panelPadding()
 *     .panel("…").contentPadding().content(rowsHtml).build()
 * </pre>
 *
 * <p>Panel and content are raw HTML. {@code contentFirst()} flips the DOM
 * order (right-panel reading order); {@code panelWidths} takes the responsive
 * pf-m-width-* tokens verbatim.
 */
@TemplateData
public final class Sidebar {

    private final String id;
    private final boolean panelRight;
    private final boolean gutter;
    private final boolean stack;
    private final boolean split;
    private final boolean panelSecondary;
    private final boolean panelPadding;
    private final boolean panelStatic;
    private final boolean panelSticky;
    private final String panelWidths;
    private final String panelStyle;
    private final String panelHtml;
    private final boolean contentPadding;
    private final String contentStyle;
    private final String contentHtml;
    private final boolean contentFirst;

    private Sidebar(Builder b) {
        this.id = b.id;
        this.panelRight = b.panelRight;
        this.gutter = b.gutter;
        this.stack = b.stack;
        this.split = b.split;
        this.panelSecondary = b.panelSecondary;
        this.panelPadding = b.panelPadding;
        this.panelStatic = b.panelStatic;
        this.panelSticky = b.panelSticky;
        this.panelWidths = b.panelWidths;
        this.panelStyle = b.panelStyle;
        this.panelHtml = b.panelHtml;
        this.contentPadding = b.contentPadding;
        this.contentStyle = b.contentStyle;
        this.contentHtml = b.contentHtml;
        this.contentFirst = b.contentFirst;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    /** Root modifier-class suffix, leading-space separated. */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (panelRight) {
            sb.append(" pf-m-panel-right");
        }
        if (gutter) {
            sb.append(" pf-m-gutter");
        }
        if (stack) {
            sb.append(" pf-m-stack");
        }
        if (split) {
            sb.append(" pf-m-split");
        }
        return sb.toString();
    }

    /** Panel modifier-class suffix, leading-space separated. */
    public String panelClasses() {
        StringBuilder sb = new StringBuilder();
        if (panelStatic) {
            sb.append(" pf-m-static");
        }
        if (panelSticky) {
            sb.append(" pf-m-sticky");
        }
        if (panelSecondary) {
            sb.append(" pf-m-secondary");
        }
        if (panelPadding) {
            sb.append(" pf-m-padding");
        }
        if (panelWidths != null) {
            sb.append(' ').append(panelWidths);
        }
        return sb.toString();
    }

    /** Content modifier-class suffix, leading-space separated. */
    public String contentClasses() {
        return contentPadding ? " pf-m-padding" : "";
    }

    public String panelStyle() {
        return panelStyle;
    }

    public String panelHtml() {
        return panelHtml;
    }

    public String contentStyle() {
        return contentStyle;
    }

    public String contentHtml() {
        return contentHtml;
    }

    public boolean isContentFirst() {
        return contentFirst;
    }

    public static final class Builder {
        private String id;
        private boolean panelRight;
        private boolean gutter;
        private boolean stack;
        private boolean split;
        private boolean panelSecondary;
        private boolean panelPadding;
        private boolean panelStatic;
        private boolean panelSticky;
        private String panelWidths;
        private String panelStyle;
        private String panelHtml;
        private boolean contentPadding;
        private String contentStyle;
        private String contentHtml;
        private boolean contentFirst;

        private Builder() {
        }

        public Builder panelRight() {
            this.panelRight = true;
            return this;
        }

        public Builder gutter() {
            this.gutter = true;
            return this;
        }

        /** Panel stacked above the content (pf-m-stack). */
        public Builder stack() {
            this.stack = true;
            return this;
        }

        /** Divider line between panel and content (pf-m-split). */
        public Builder split() {
            this.split = true;
            return this;
        }

        public Builder panelSecondary() {
            this.panelSecondary = true;
            return this;
        }

        public Builder panelPadding() {
            this.panelPadding = true;
            return this;
        }

        /** Panel scrolls away with the content (pf-m-static). */
        public Builder panelStatic() {
            this.panelStatic = true;
            return this;
        }

        /** Panel stays pinned while content scrolls (pf-m-sticky). */
        public Builder panelSticky() {
            this.panelSticky = true;
            return this;
        }

        /** Responsive width tokens verbatim (e.g. "pf-m-width-25 pf-m-width-33-on-lg"). */
        public Builder panelWidths(String panelWidths) {
            this.panelWidths = panelWidths;
            return this;
        }

        public Builder panelStyle(String panelStyle) {
            this.panelStyle = panelStyle;
            return this;
        }

        /** Panel content (raw HTML). */
        public Builder panel(String panelHtml) {
            this.panelHtml = panelHtml;
            return this;
        }

        public Builder contentPadding() {
            this.contentPadding = true;
            return this;
        }

        public Builder contentStyle(String contentStyle) {
            this.contentStyle = contentStyle;
            return this;
        }

        /** Main content (raw HTML). */
        public Builder content(String contentHtml) {
            this.contentHtml = contentHtml;
            return this;
        }

        /** Content before the panel in the DOM (right-panel reading order). */
        public Builder contentFirst() {
            this.contentFirst = true;
            return this;
        }

        public Sidebar build() {
            if (panelHtml == null || contentHtml == null) {
                throw new IllegalStateException("A sidebar needs panel(...) and content(...)");
            }
            return new Sidebar(this);
        }
    }
}
