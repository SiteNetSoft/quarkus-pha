package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Button component ({@code pf-v6-c-button}).
 *
 * <pre>
 * Button.of("Save").build()                              // primary by default
 * Button.of("Cancel").variant("link").build()
 * Button.of("Open").href("#target").variant("secondary").build()
 * Button.icon("fa:xmark", "Close").variant("plain").build()
 * Button.of("Saving…").variant("primary").loading("Saving").build()
 * </pre>
 *
 * <p>Covers the full parameter surface of the runtime template: variants,
 * sizes, circle/block/inline, disabled and aria-disabled, loading progress,
 * count badges, stateful notification states, the favorite/settings/hamburger
 * plain buttons, links, submit/reset types, and the span/div tag overrides
 * for nesting inside other interactive elements. All fluents live on the
 * Builder; Qute only sees the built object's accessors.
 */
@TemplateData
public final class Button {

    private final String text;
    private final String id;
    private final String variant;
    private final String size;
    private final boolean circle;
    private final boolean block;
    private final boolean inline;
    private final boolean disabled;
    private final boolean ariaDisabled;
    private final boolean clicked;
    private final boolean noPadding;
    private final String spinnerLabel;
    private final String tag;
    private final String href;
    private final String type;
    private final String ariaLabel;
    private final String ariaExpanded;
    private final String icon;
    private final boolean iconAtEnd;
    private final Integer count;
    private final boolean countRead;
    private final String state;
    private final boolean favorite;
    private final boolean favorited;
    private final boolean settings;
    private final boolean hamburger;
    private final String hamburgerVariant;

    private Button(Builder b) {
        this.text = b.text;
        this.id = b.id;
        this.variant = b.variant;
        this.size = b.size;
        this.circle = b.circle;
        this.block = b.block;
        this.inline = b.inline;
        this.disabled = b.disabled;
        this.ariaDisabled = b.ariaDisabled;
        this.clicked = b.clicked;
        this.noPadding = b.noPadding;
        this.spinnerLabel = b.spinnerLabel;
        this.tag = b.tag;
        this.href = b.href;
        this.type = b.type;
        this.ariaLabel = b.ariaLabel;
        this.ariaExpanded = b.ariaExpanded;
        this.icon = b.icon;
        this.iconAtEnd = b.iconAtEnd;
        this.count = b.count;
        this.countRead = b.countRead;
        this.state = b.state;
        this.favorite = b.favorite;
        this.favorited = b.favorited;
        this.settings = b.settings;
        this.hamburger = b.hamburger;
        this.hamburgerVariant = b.hamburgerVariant;
    }

    /** Text button (primary unless a variant is set). */
    public static Builder of(String text) {
        Builder b = new Builder();
        b.text = Objects.requireNonNull(text, "text");
        return b;
    }

    /** Icon-only button with its accessible label. */
    public static Builder icon(String icon, String ariaLabel) {
        Builder b = new Builder();
        b.icon = Objects.requireNonNull(icon, "icon");
        b.ariaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return b;
    }

    /** Favorite star button (plain), toggled via {@code asFavorited()}. */
    public static Builder favorite(String ariaLabel) {
        Builder b = new Builder();
        b.favorite = true;
        b.ariaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return b;
    }

    /** Settings gear button (plain). */
    public static Builder settings(String ariaLabel) {
        Builder b = new Builder();
        b.settings = true;
        b.ariaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return b;
    }

    /** Hamburger menu button (plain); variant "expand" or "collapse" via {@code hamburgerVariant()}. */
    public static Builder hamburger(String ariaLabel) {
        Builder b = new Builder();
        b.hamburger = true;
        b.ariaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return b;
    }

    public String text() {
        return text;
    }

    public String id() {
        return id;
    }

    public String variant() {
        return variant;
    }

    /** True unless a special plain button (favorite/settings/hamburger) suppresses the variant class. */
    public boolean isShowVariant() {
        return !(favorite || settings || hamburger);
    }

    public String size() {
        return size;
    }

    public boolean isCircle() {
        return circle;
    }

    public boolean isBlock() {
        return block;
    }

    public boolean isInline() {
        return inline;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isAriaDisabled() {
        return ariaDisabled;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isNoPadding() {
        return noPadding;
    }

    public boolean isLoading() {
        return spinnerLabel != null;
    }

    public String spinnerLabel() {
        return spinnerLabel;
    }

    /** Tag override ("span"/"div"), or null for the default button/anchor. */
    public String tag() {
        return tag;
    }

    public String href() {
        return href;
    }

    /** Button type attribute (submit | reset | button). */
    public String buttonType() {
        return type != null ? type : "button";
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public String ariaExpanded() {
        return ariaExpanded;
    }

    public String icon() {
        return icon;
    }

    public boolean isIconAtEnd() {
        return iconAtEnd;
    }

    public Integer count() {
        return count;
    }

    public boolean isCountRead() {
        return countRead;
    }

    public String state() {
        return state;
    }

    public boolean isStateful() {
        return state != null;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isSettings() {
        return settings;
    }

    public boolean isHamburger() {
        return hamburger;
    }

    public String hamburgerVariant() {
        return hamburgerVariant;
    }

    public static final class Builder {

        private String text;
        private String id;
        private String variant;
        private String size;
        private boolean circle;
        private boolean block;
        private boolean inline;
        private boolean disabled;
        private boolean ariaDisabled;
        private boolean clicked;
        private boolean noPadding;
        private String spinnerLabel;
        private String tag;
        private String href;
        private String type;
        private String ariaLabel;
        private String ariaExpanded;
        private String icon;
        private boolean iconAtEnd;
        private Integer count;
        private boolean countRead;
        private String state;
        private boolean favorite;
        private boolean favorited;
        private boolean settings;
        private boolean hamburger;
        private String hamburgerVariant;

        private Builder() {
        }

        /** DOM id on the button root. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** primary (default) | secondary | tertiary | danger | warning | link | plain | control | stateful. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        /** sm | lg. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        /** Circular icon-only button. */
        public Builder asCircle() {
            this.circle = true;
            return this;
        }

        /** Block-level button spanning its container. */
        public Builder asBlock() {
            this.block = true;
            return this;
        }

        /** Inline flow (used with the link variant). */
        public Builder asInline() {
            this.inline = true;
            return this;
        }

        public Builder asDisabled() {
            this.disabled = true;
            return this;
        }

        /** aria-disabled styling that keeps the button focusable. */
        public Builder asAriaDisabled() {
            this.ariaDisabled = true;
            return this;
        }

        /** Active/clicked styling. */
        public Builder asClicked() {
            this.clicked = true;
            return this;
        }

        /** Removes the plain button's padding so the icon sits flush. */
        public Builder asNoPadding() {
            this.noPadding = true;
            return this;
        }

        /** In-progress state with a spinner labelled for AT. */
        public Builder loading(String spinnerLabel) {
            this.spinnerLabel = Objects.requireNonNull(spinnerLabel, "spinnerLabel");
            return this;
        }

        /** Render as a non-interactive span (e.g. link styling inside another anchor). */
        public Builder asSpan() {
            this.tag = "span";
            return this;
        }

        /** Render as a div. */
        public Builder asDiv() {
            this.tag = "div";
            return this;
        }

        /** Renders the button as an anchor. */
        public Builder href(String href) {
            this.href = href;
            return this;
        }

        /** submit | reset (default is "button"). */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** "true" | "false" for toggling buttons. */
        public Builder ariaExpanded(String ariaExpanded) {
            this.ariaExpanded = ariaExpanded;
            return this;
        }

        /** Icon next to the text, e.g. {@code "fa:arrow-up-right-from-square"}. */
        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        /** Place the icon after the text. */
        public Builder iconAtEnd() {
            this.iconAtEnd = true;
            return this;
        }

        /** Count badge after the text. */
        public Builder count(int count) {
            this.count = count;
            return this;
        }

        /** Read (muted) styling on the count badge. */
        public Builder countRead() {
            this.countRead = true;
            return this;
        }

        /** Stateful notification state: read | unread | attention (pair with variant "stateful"). */
        public Builder state(String state) {
            this.state = state;
            return this;
        }

        /** Filled favorite star. */
        public Builder asFavorited() {
            this.favorited = true;
            return this;
        }

        /** Hamburger animation variant: expand | collapse. */
        public Builder hamburgerVariant(String hamburgerVariant) {
            this.hamburgerVariant = hamburgerVariant;
            return this;
        }

        public Button build() {
            return new Button(this);
        }
    }
}
