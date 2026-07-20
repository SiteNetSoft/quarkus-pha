package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Content component ({@code pf-v6-c-content}).
 *
 * <pre>
 * Content.element("p").text("A paragraph.").build()
 * Content.element("a").href("#target").visited().text("Visited link").build()
 * Content.element("ul").html("&lt;li&gt;One&lt;/li&gt;...").build()
 * Content.wrapper().html("&lt;h2&gt;Title&lt;/h2&gt;&lt;p&gt;...&lt;/p&gt;").build()
 * </pre>
 *
 * <p>Element mode renders the tag itself with {@code pf-v6-c-content--{tag}};
 * wrapper mode renders a {@code pf-v6-c-content} div whose raw-HTML children
 * pick up PF typography automatically. Content is {@code text} (escaped) or
 * {@code html} (raw) — one or the other.
 */
@TemplateData
public final class Content {

    private final String component;
    private final String text;
    private final String htmlBody;
    private final String id;
    private final String href;
    private final boolean editorial;
    private final boolean visited;
    private final boolean plainList;

    private Content(Builder b) {
        this.component = b.component;
        this.text = b.text;
        this.htmlBody = b.htmlBody;
        this.id = b.id;
        this.href = b.href;
        this.editorial = b.editorial;
        this.visited = b.visited;
        this.plainList = b.plainList;
    }

    /** Element mode: h1-h6 | p | a | small | blockquote | pre | hr | ul | ol | dl | li | dt | dd. */
    public static Builder element(String component) {
        Builder b = new Builder();
        b.component = Objects.requireNonNull(component, "component");
        return b;
    }

    /** Wrapper mode: a pf-v6-c-content div styling its raw-HTML descendants. */
    public static Builder wrapper() {
        return new Builder();
    }

    public boolean isElement() {
        return component != null;
    }

    public String component() {
        return component;
    }

    public String text() {
        return text;
    }

    public String htmlBody() {
        return htmlBody;
    }

    public String id() {
        return id;
    }

    public String href() {
        return href;
    }

    /** Modifier-class suffix, leading-space separated. */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (editorial) {
            sb.append(" pf-m-editorial");
        }
        if (visited) {
            sb.append(" pf-m-visited");
        }
        if (plainList) {
            sb.append(" pf-m-plain");
        }
        return sb.toString();
    }

    public static final class Builder {
        private String component;
        private String text;
        private String htmlBody;
        private String id;
        private String href;
        private boolean editorial;
        private boolean visited;
        private boolean plainList;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Simple text content (escaped). */
        public Builder text(String text) {
            this.text = text;
            return this;
        }

        /** Raw HTML content (element children or wrapper body). */
        public Builder html(String html) {
            this.htmlBody = html;
            return this;
        }

        /** Link target when component is "a". */
        public Builder href(String href) {
            this.href = href;
            return this;
        }

        /** Larger body/small text for long-form prose (pf-m-editorial). */
        public Builder editorial() {
            this.editorial = true;
            return this;
        }

        /** Visited-link styling (pf-m-visited). */
        public Builder visited() {
            this.visited = true;
            return this;
        }

        /** No bullets on ul/ol/dl (pf-m-plain). */
        public Builder plainList() {
            this.plainList = true;
            return this;
        }

        public Content build() {
            if (text == null && htmlBody == null) {
                throw new IllegalStateException("Content needs text(...) or html(...)");
            }
            if (text != null && htmlBody != null) {
                throw new IllegalStateException("text and html are mutually exclusive");
            }
            return new Content(this);
        }
    }
}
