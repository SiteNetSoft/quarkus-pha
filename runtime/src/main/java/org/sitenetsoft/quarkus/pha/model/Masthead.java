package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Masthead component ({@code pf-v6-c-masthead}).
 *
 * <pre>
 * Masthead.of("mh-inline").displayInline()
 *     .toggle("Global navigation").logoText("PHA Showcase")
 *     .content("Brand and content sit inline.").build()
 * Masthead.of("mh-logo").toggle("Global navigation")
 *     .logoImages("#", "PHA Showcase home", "/web/images/logo.svg", "/web/images/logo-dark.svg",
 *                 "Quarkus PHA", "36px")
 *     .content("Content").build()
 * </pre>
 *
 * <p>Brand anatomies: {@code logoText} (anchor + strong), {@code logoImages}
 * (anchor wrapping light/dark pf-v6-c-brand images), or raw {@code brandHtml}.
 * Content is raw HTML. Rich compositions (toolbars, composed buttons in the
 * content slot) stay on the slot shell.
 */
@TemplateData
public final class Masthead {

    private final String id;
    private final String toggleAriaLabel;
    private final String logoText;
    private final String brandHtml;
    private final String logoHref;
    private final String logoAriaLabel;
    private final String lightSrc;
    private final String darkSrc;
    private final String brandAlt;
    private final String brandHeight;
    private final String contentHtml;
    private final boolean displayStack;
    private final boolean displayInline;
    private final String extraModifiers;
    private final String style;

    private Masthead(Builder b) {
        this.id = b.id;
        this.toggleAriaLabel = b.toggleAriaLabel;
        this.logoText = b.logoText;
        this.brandHtml = b.brandHtml;
        this.logoHref = b.logoHref;
        this.logoAriaLabel = b.logoAriaLabel;
        this.lightSrc = b.lightSrc;
        this.darkSrc = b.darkSrc;
        this.brandAlt = b.brandAlt;
        this.brandHeight = b.brandHeight;
        this.contentHtml = b.contentHtml;
        this.displayStack = b.displayStack;
        this.displayInline = b.displayInline;
        this.extraModifiers = b.extraModifiers;
        this.style = b.style;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String toggleAriaLabel() {
        return toggleAriaLabel;
    }

    public String logoText() {
        return logoText;
    }

    public String brandHtml() {
        return brandHtml;
    }

    public boolean isLogoImages() {
        return lightSrc != null;
    }

    public String logoHref() {
        return logoHref;
    }

    public String logoAriaLabel() {
        return logoAriaLabel;
    }

    public String lightSrc() {
        return lightSrc;
    }

    public String darkSrc() {
        return darkSrc;
    }

    public String brandAlt() {
        return brandAlt;
    }

    public String brandHeight() {
        return brandHeight;
    }

    public String contentHtml() {
        return contentHtml;
    }

    /** Modifier-class suffix, leading-space separated. */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (displayStack) {
            sb.append(" pf-m-display-stack");
        }
        if (displayInline) {
            sb.append(" pf-m-display-inline");
        }
        if (extraModifiers != null) {
            sb.append(' ').append(extraModifiers);
        }
        return sb.toString();
    }

    public String style() {
        return style;
    }

    public static final class Builder {
        private String id;
        private String toggleAriaLabel;
        private String logoText;
        private String brandHtml;
        private String logoHref;
        private String logoAriaLabel;
        private String lightSrc;
        private String darkSrc;
        private String brandAlt;
        private String brandHeight;
        private String contentHtml;
        private boolean displayStack;
        private boolean displayInline;
        private String extraModifiers;
        private String style;

        private Builder() {
        }

        /** Span-wrapped plain hamburger toggle with this accessible name. */
        public Builder toggle(String ariaLabel) {
            this.toggleAriaLabel = ariaLabel;
            return this;
        }

        /** Text logo: an anchor wrapping the name in a strong. */
        public Builder logoText(String logoText) {
            this.logoText = logoText;
            return this;
        }

        /** Arbitrary brand-area HTML (no anchor). */
        public Builder brandHtml(String brandHtml) {
            this.brandHtml = brandHtml;
            return this;
        }

        /** Anchor wrapping theme-paired pf-v6-c-brand images (pha.css hides the off-theme one). */
        public Builder logoImages(String href, String ariaLabel, String lightSrc, String darkSrc,
                                  String alt, String height) {
            this.logoHref = Objects.requireNonNull(href, "href");
            this.logoAriaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
            this.lightSrc = Objects.requireNonNull(lightSrc, "lightSrc");
            this.darkSrc = Objects.requireNonNull(darkSrc, "darkSrc");
            this.brandAlt = Objects.requireNonNull(alt, "alt");
            this.brandHeight = Objects.requireNonNull(height, "height");
            return this;
        }

        /** Content-area HTML. */
        public Builder content(String contentHtml) {
            this.contentHtml = contentHtml;
            return this;
        }

        /** Brand row above content (pf-m-display-stack). */
        public Builder displayStack() {
            this.displayStack = true;
            return this;
        }

        /** Brand and content side by side (pf-m-display-inline). */
        public Builder displayInline() {
            this.displayInline = true;
            return this;
        }

        /** Verbatim extra modifier classes (responsive display/inset combos). */
        public Builder modifiers(String extraModifiers) {
            this.extraModifiers = extraModifiers;
            return this;
        }

        /** Inline style on the header root. */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        public Masthead build() {
            if (contentHtml == null) {
                throw new IllegalStateException("A masthead needs content(...)");
            }
            if (logoText == null && brandHtml == null && lightSrc == null) {
                throw new IllegalStateException("A masthead needs a brand (logoText/brandHtml/logoImages)");
            }
            return new Masthead(this);
        }
    }
}
