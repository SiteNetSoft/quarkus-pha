package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Brand component ({@code pf-v6-c-brand}).
 *
 * <pre>
 * Brand.of("/web/images/logo.svg", "Quarkus PHA logo").id("brand-basic").build()
 * Brand.of(src, alt).height("36px").build()
 * Brand.of(fallbackSrc, alt).sources("&lt;source media=... srcset=... /&gt;").build()  // picture
 * </pre>
 *
 * <p>{@code sources(html)} switches to the responsive {@code <picture>}
 * anatomy; width/height emit the PF brand CSS variables.
 */
@TemplateData
public final class Brand {

    private final String src;
    private final String alt;
    private final String id;
    private final String width;
    private final String height;
    private final String sourcesHtml;

    private Brand(Builder b) {
        this.src = b.src;
        this.alt = b.alt;
        this.id = b.id;
        this.width = b.width;
        this.height = b.height;
        this.sourcesHtml = b.sourcesHtml;
    }

    public static Builder of(String src, String alt) {
        Builder b = new Builder();
        b.src = Objects.requireNonNull(src, "src");
        b.alt = Objects.requireNonNull(alt, "alt");
        return b;
    }

    public String src() {
        return src;
    }

    public String alt() {
        return alt;
    }

    public String id() {
        return id;
    }

    public boolean isPicture() {
        return sourcesHtml != null;
    }

    public String sourcesHtml() {
        return sourcesHtml;
    }

    /** Inline style setting the PF brand size variables; null when unsized. */
    public String sizeStyle() {
        if (width != null && height != null) {
            return "--pf-v6-c-brand--Width: " + width + "; --pf-v6-c-brand--Height: " + height;
        }
        if (width != null) {
            return "--pf-v6-c-brand--Width: " + width;
        }
        if (height != null) {
            return "--pf-v6-c-brand--Height: " + height;
        }
        return null;
    }

    public static final class Builder {
        private String src;
        private String alt;
        private String id;
        private String width;
        private String height;
        private String sourcesHtml;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** CSS length — width auto-scales height. */
        public Builder width(String width) {
            this.width = width;
            return this;
        }

        /** CSS length — height auto-scales width. */
        public Builder height(String height) {
            this.height = height;
            return this;
        }

        /** Raw &lt;source&gt; elements — switches to the responsive picture anatomy. */
        public Builder sources(String sourcesHtml) {
            this.sourcesHtml = sourcesHtml;
            return this;
        }

        public Brand build() {
            return new Brand(this);
        }
    }
}
