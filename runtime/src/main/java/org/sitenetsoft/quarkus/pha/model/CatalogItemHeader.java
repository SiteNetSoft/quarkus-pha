package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Catalog item header ({@code catalog-item-header-pf},
 * PatternFly catalog-view extension).
 *
 * <pre>
 * CatalogItemHeader.of("Quarkus pha")
 *     .iconClass("pf-v6-c-icon fas fa-box")
 *     .vendorHtml("provided by &lt;a href=…&gt;SiteNetSoft&lt;/a&gt;")
 *     .build();
 * </pre>
 *
 * <p>{@code iconImg(src, alt)} and {@code iconClass(...)} are mutually
 * exclusive. {@code vendorHtml} is raw HTML — plain text works as-is, and the
 * subtitle can carry markup such as a provider link.
 */
@TemplateData
public final class CatalogItemHeader {

    private final String id;
    private final String title;
    private final String vendorHtml;
    private final String iconImg;
    private final String iconAlt;
    private final String iconClass;
    private final String headingLevel;

    private CatalogItemHeader(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.vendorHtml = b.vendorHtml;
        this.iconImg = b.iconImg;
        this.iconAlt = b.iconAlt;
        this.iconClass = b.iconClass;
        this.headingLevel = b.headingLevel;
    }

    public static Builder of(String title) {
        Builder b = new Builder();
        b.title = Objects.requireNonNull(title, "title");
        return b;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String vendorHtml() {
        return vendorHtml;
    }

    public String iconImg() {
        return iconImg;
    }

    public String iconAlt() {
        return iconAlt != null ? iconAlt : "";
    }

    public String iconClass() {
        return iconClass;
    }

    public String headingTag() {
        return headingLevel != null ? headingLevel : "h1";
    }

    public static final class Builder {
        private String id;
        private String title;
        private String vendorHtml;
        private String iconImg;
        private String iconAlt;
        private String iconClass;
        private String headingLevel;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Vendor subtitle, raw HTML (plain text or markup such as a link). */
        public Builder vendorHtml(String vendorHtml) {
            this.vendorHtml = vendorHtml;
            return this;
        }

        /** Icon as an image URL (mutually exclusive with iconClass). */
        public Builder iconImg(String src, String alt) {
            this.iconImg = src;
            this.iconAlt = alt;
            return this;
        }

        /** Icon as a CSS glyph class (mutually exclusive with iconImg). */
        public Builder iconClass(String iconClass) {
            this.iconClass = iconClass;
            return this;
        }

        /** h1 (default) | h2 | ... on the title. */
        public Builder headingLevel(String headingLevel) {
            this.headingLevel = headingLevel;
            return this;
        }

        public CatalogItemHeader build() {
            if (iconImg != null && iconClass != null) {
                throw new IllegalStateException("iconImg and iconClass are mutually exclusive");
            }
            return new CatalogItemHeader(this);
        }
    }
}
