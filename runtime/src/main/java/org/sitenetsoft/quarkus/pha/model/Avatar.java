package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

/**
 * Typed model for the Avatar component ({@code pf-v6-c-avatar}).
 *
 * <pre>
 * Avatar.of(imageUrl, "User avatar").size("lg").bordered().build()   // img flavour
 * Avatar.initials("C", "Avatar with initial C").color("red").build() // div + __initials
 * Avatar.silhouette("Red avatar").color("red").build()               // div + person SVG
 * Avatar.svg(customSvgMarkup, "Custom avatar").build()               // div + raw SVG
 * </pre>
 *
 * <p>Three anatomies: an {@code <img>} (src/alt), or a {@code <div role="img">}
 * holding either an initials span or raw SVG content. {@code color(...)} implies
 * {@code pf-m-colorful} (PF 6.6 colorful flavour, div anatomies only).
 */
@TemplateData
public final class Avatar {

    /** PF's default person silhouette, used by the colorful flavour. */
    static final String PERSON_SVG = """
            <svg aria-hidden="true" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 36 36">
              <path
                fill="currentColor"
                d="M17.7,20.1c-3.5,0-6.4-2.9-6.4-6.4s2.9-6.4,6.4-6.4,6.4,2.9,6.4,6.4-2.8,6.4-6.4,6.4Z"
              />
              <path
                fill="currentColor"
                d="M30.6,36c-.4-3.9-1.3-9-2.9-11-1.1-1.4-2.3-2.2-3.5-2.6s-1.8-.6-6.3-.6-6.1.7-6.1.7c-1.2.4-2.4,1.2-3.4,2.6-1.7,1.9-2.6,7.1-3,10.9h25.2Z"
              />
            </svg>""";

    private final String src;
    private final String alt;
    private final String initials;
    private final String svgHtml;
    private final String ariaLabel;
    private final String id;
    private final String size;
    private final String color;
    private final boolean bordered;

    private Avatar(Builder b) {
        this.src = b.src;
        this.alt = b.alt;
        this.initials = b.initials;
        this.svgHtml = b.svgHtml;
        this.ariaLabel = b.ariaLabel;
        this.id = b.id;
        this.size = b.size;
        this.color = b.color;
        this.bordered = b.bordered;
    }

    /** Image avatar — renders an {@code <img>}. */
    public static Builder of(String src, String alt) {
        Builder b = new Builder();
        b.src = src;
        b.alt = alt;
        return b;
    }

    /** Initials avatar — a div holding an aria-hidden {@code __initials} span. */
    public static Builder initials(String initials, String ariaLabel) {
        Builder b = new Builder();
        b.initials = initials;
        b.ariaLabel = ariaLabel;
        return b;
    }

    /** SVG avatar — a div holding the given raw SVG markup (aria-hidden recommended). */
    public static Builder svg(String svgHtml, String ariaLabel) {
        Builder b = new Builder();
        b.svgHtml = svgHtml;
        b.ariaLabel = ariaLabel;
        return b;
    }

    /** SVG avatar with PF's default person silhouette (the colorful-flavour default). */
    public static Builder silhouette(String ariaLabel) {
        return svg(PERSON_SVG, ariaLabel);
    }

    public boolean isImg() {
        return src != null;
    }

    public boolean isInitialsShape() {
        return initials != null;
    }

    public String src() {
        return src;
    }

    public String alt() {
        return alt;
    }

    public String initialsText() {
        return initials;
    }

    public String svgHtml() {
        return svgHtml;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public String id() {
        return id;
    }

    /** Modifier-class suffix, leading-space separated (colorful+color, size, bordered). */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (color != null) {
            sb.append(" pf-m-colorful pf-m-").append(color);
        }
        if (size != null) {
            sb.append(" pf-m-").append(size);
        }
        if (bordered) {
            sb.append(" pf-m-bordered");
        }
        return sb.toString();
    }

    public static final class Builder {
        private String src;
        private String alt;
        private String initials;
        private String svgHtml;
        private String ariaLabel;
        private String id;
        private String size;
        private String color;
        private boolean bordered;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** sm | md | lg | xl. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        /** Colorful flavour color: red | orangered | orange | yellow | green | teal | blue | purple | gray. */
        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder bordered() {
            this.bordered = true;
            return this;
        }

        public Avatar build() {
            return new Avatar(this);
        }
    }
}
