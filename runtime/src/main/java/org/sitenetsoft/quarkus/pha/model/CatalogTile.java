package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Catalog tile ({@code catalog-tile-pf}, PatternFly
 * catalog-view extension).
 *
 * <pre>
 * CatalogTile.of("HTMX")
 *     .id("ct-htmx").featured()
 *     .iconClass("pf-v6-c-icon fas fa-arrow-right-arrow-left")
 *     .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-certificate", "Certified"))
 *     .vendor("Big Sky Software")
 *     .description("High-power tools for HTML …")
 *     .build();
 * </pre>
 *
 * <p>{@code href(...)} renders the whole tile as an anchor instead of a
 * button-role div. {@code description} clamps per the extension CSS (3 lines,
 * 1 with a footer); {@code bodyHtml(...)} replaces it with arbitrary unclamped
 * body content (PF React's children).
 */
@TemplateData
public final class CatalogTile {

    private final String id;
    private final String title;
    private final String href;
    private final String vendor;
    private final String description;
    private final String bodyHtml;
    private final String footer;
    private final boolean featured;
    private final String iconImg;
    private final String iconAlt;
    private final String iconClass;
    private final List<Badge> badges;

    private CatalogTile(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.href = b.href;
        this.vendor = b.vendor;
        this.description = b.description;
        this.bodyHtml = b.bodyHtml;
        this.footer = b.footer;
        this.featured = b.featured;
        this.iconImg = b.iconImg;
        this.iconAlt = b.iconAlt;
        this.iconClass = b.iconClass;
        this.badges = List.copyOf(b.badges);
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

    public String href() {
        return href;
    }

    public String vendor() {
        return vendor;
    }

    public String description() {
        return description;
    }

    public String bodyHtml() {
        return bodyHtml;
    }

    public String footer() {
        return footer;
    }

    public boolean featured() {
        return featured;
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

    public List<Badge> badges() {
        return badges;
    }

    /** Badge in the tile-header badge container: an icon glyph with an accessible label, or plain text. */
    @TemplateData
    public record Badge(String iconClass, String label, String text) {

        public static Badge icon(String iconClass, String label) {
            return new Badge(Objects.requireNonNull(iconClass, "iconClass"),
                    Objects.requireNonNull(label, "label"), null);
        }

        public static Badge text(String text) {
            return new Badge(null, null, Objects.requireNonNull(text, "text"));
        }
    }

    public static final class Builder {
        private String id;
        private String title;
        private String href;
        private String vendor;
        private String description;
        private String bodyHtml;
        private String footer;
        private boolean featured;
        private String iconImg;
        private String iconAlt;
        private String iconClass;
        private final List<Badge> badges = new ArrayList<>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Renders the whole tile as an anchor. */
        public Builder href(String href) {
            this.href = href;
            return this;
        }

        public Builder vendor(String vendor) {
            this.vendor = vendor;
            return this;
        }

        /** Clamped description text (3 lines; 1 when a footer is present). */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /** Arbitrary unclamped body content (raw HTML) instead of a description. */
        public Builder bodyHtml(String bodyHtml) {
            this.bodyHtml = bodyHtml;
            return this;
        }

        /** Short status text pinned to the tile bottom. */
        public Builder footer(String footer) {
            this.footer = footer;
            return this;
        }

        public Builder featured() {
            this.featured = true;
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

        public Builder badge(Badge badge) {
            this.badges.add(badge);
            return this;
        }

        public CatalogTile build() {
            if (iconImg != null && iconClass != null) {
                throw new IllegalStateException("iconImg and iconClass are mutually exclusive");
            }
            if (description != null && bodyHtml != null) {
                throw new IllegalStateException("description and bodyHtml are mutually exclusive");
            }
            return new CatalogTile(this);
        }
    }
}
