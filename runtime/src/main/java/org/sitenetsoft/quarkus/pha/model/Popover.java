package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Popover component ({@code pf-v6-c-popover}) — generates
 * the full live pattern: trigger button, Alpine-positioned wrapper, and the
 * popover box.
 *
 * <pre>
 * Popover.of("po-basic").trigger("Show popover")
 *     .title("Popover header").dismissable()
 *     .body("Popovers are triggered by click…")
 *     .footerText("Popover footer").build()
 * Popover.of("po-danger").staticOpen().trigger("Delete").triggerVariant("danger")
 *     .variant("danger").titlePlain("Delete this project?")
 *     .body("This action cannot be undone…")
 *     .footerButton("Delete", "pf-m-danger", false)
 *     .footerButton("Cancel", "pf-m-link", false).build()
 * Popover.of("po-hover").hoverable().trigger("Hover or focus me")
 *     .title("Hoverable popover").body("…").build()
 * </pre>
 *
 * <p>Open modes: click (default), {@code hoverable()} (hover/focus), or
 * {@code staticOpen()} (always visible, no Alpine). Position accepts the 4
 * sides plus diagonal tokens (left-top, bottom-right, …). The dialog is named
 * by the title (aria-labelledby) or {@code ariaLabel};
 * {@code describedByBody()} wires aria-describedby onto a {id}-body id.
 */
@TemplateData
public final class Popover {

    /** One footer action button. */
    @TemplateData
    public static final class FooterButton {
        private final String text;
        private final String modifiers;
        private final boolean closes;

        private FooterButton(String text, String modifiers, boolean closes) {
            this.text = text;
            this.modifiers = modifiers;
            this.closes = closes;
        }

        public String text() {
            return text;
        }

        public String modifiers() {
            return modifiers;
        }

        public boolean isCloses() {
            return closes;
        }
    }

    private static final String DEFAULT_CHROME_STYLE =
            "position: relative; display: inline-block; padding-top: 1rem";
    private static final String DEFAULT_WRAPPER_STYLE =
            "position: absolute; bottom: 100%; left: 50%; transform: translateX(-50%); width: 320px; z-index: 300";

    private final String id;
    private final String triggerText;
    private final String triggerVariant;
    private final boolean hoverable;
    private final boolean staticOpen;
    private final String chromeStyle;
    private final String wrapperStyle;
    private final String position;
    private final String variant;
    private final boolean noPadding;
    private final boolean widthAuto;
    private final String title;
    private final String titleIcon;
    private final boolean titlePlain;
    private final String ariaLabel;
    private final boolean describedByBody;
    private final boolean dismissable;
    private final String body;
    private final String footerText;
    private final List<FooterButton> footerButtons;

    private Popover(Builder b) {
        this.id = b.id;
        this.triggerText = b.triggerText;
        this.triggerVariant = b.triggerVariant;
        this.hoverable = b.hoverable;
        this.staticOpen = b.staticOpen;
        this.chromeStyle = b.chromeStyle;
        this.wrapperStyle = b.wrapperStyle;
        this.position = b.position;
        this.variant = b.variant;
        this.noPadding = b.noPadding;
        this.widthAuto = b.widthAuto;
        this.title = b.title;
        this.titleIcon = b.titleIcon;
        this.titlePlain = b.titlePlain;
        this.ariaLabel = b.ariaLabel;
        this.describedByBody = b.describedByBody;
        this.dismissable = b.dismissable;
        this.body = b.body;
        this.footerText = b.footerText;
        this.footerButtons = List.copyOf(b.footerButtons);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String triggerText() {
        return triggerText;
    }

    public String triggerVariant() {
        return triggerVariant != null ? triggerVariant : "secondary";
    }

    public boolean isHoverable() {
        return hoverable;
    }

    public boolean isStaticOpen() {
        return staticOpen;
    }

    public String chromeStyle() {
        return chromeStyle != null ? chromeStyle : DEFAULT_CHROME_STYLE;
    }

    public String wrapperStyle() {
        return wrapperStyle != null ? wrapperStyle : DEFAULT_WRAPPER_STYLE;
    }

    /** Modifier-class suffix on the popover box, leading-space separated. */
    public String boxModifiers() {
        StringBuilder sb = new StringBuilder();
        sb.append(" pf-m-").append(position != null ? position : "top");
        if (variant != null) {
            sb.append(" pf-m-").append(variant);
        }
        if (noPadding) {
            sb.append(" pf-m-no-padding");
        }
        if (widthAuto) {
            sb.append(" pf-m-width-auto");
        }
        return sb.toString();
    }

    public String title() {
        return title;
    }

    public String titleIcon() {
        return titleIcon;
    }

    public boolean isTitlePlain() {
        return titlePlain;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public boolean isDescribedByBody() {
        return describedByBody;
    }

    public boolean isDismissable() {
        return dismissable;
    }

    public String body() {
        return body;
    }

    public String footerText() {
        return footerText;
    }

    public boolean isHasFooterButtons() {
        return !footerButtons.isEmpty();
    }

    public List<FooterButton> footerButtons() {
        return footerButtons;
    }

    public static final class Builder {
        private String id;
        private String triggerText;
        private String triggerVariant;
        private boolean hoverable;
        private boolean staticOpen;
        private String chromeStyle;
        private String wrapperStyle;
        private String position;
        private String variant;
        private boolean noPadding;
        private boolean widthAuto;
        private String title;
        private String titleIcon;
        private boolean titlePlain;
        private String ariaLabel;
        private boolean describedByBody;
        private boolean dismissable;
        private String body;
        private String footerText;
        private final List<FooterButton> footerButtons = new ArrayList<>();

        private Builder() {
        }

        /** Trigger button text (secondary unless triggerVariant is set). */
        public Builder trigger(String triggerText) {
            this.triggerText = triggerText;
            return this;
        }

        public Builder triggerVariant(String triggerVariant) {
            this.triggerVariant = triggerVariant;
            return this;
        }

        /** Open on hover/focus instead of click. */
        public Builder hoverable() {
            this.hoverable = true;
            return this;
        }

        /** Always visible — no Alpine state (frozen documentation shape). */
        public Builder staticOpen() {
            this.staticOpen = true;
            return this;
        }

        /** Outer chrome style (default anchors the trigger with top padding). */
        public Builder chromeStyle(String chromeStyle) {
            this.chromeStyle = chromeStyle;
            return this;
        }

        /** Positioning-wrapper style (default: centered above, 320px, z-index 300). */
        public Builder wrapperStyle(String wrapperStyle) {
            this.wrapperStyle = wrapperStyle;
            return this;
        }

        /** top (default) | bottom | left | right | left-top | bottom-right | … */
        public Builder position(String position) {
            this.position = position;
            return this;
        }

        /** danger | warning | success | info | custom severity styling. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        public Builder noPadding() {
            this.noPadding = true;
            return this;
        }

        public Builder widthAuto() {
            this.widthAuto = true;
            return this;
        }

        /** Header title (h4-wrapped) — names the dialog via aria-labelledby. */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /** Icon before the title text. */
        public Builder titleIcon(String titleIcon) {
            this.titleIcon = titleIcon;
            return this;
        }

        /** Bare __title-text header without the h4 wrapper (confirmation anatomy). */
        public Builder titlePlain(String title) {
            this.title = title;
            this.titlePlain = true;
            return this;
        }

        /** Accessible dialog name when there is no title. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Give the body an id and wire aria-describedby onto the dialog. */
        public Builder describedByBody() {
            this.describedByBody = true;
            return this;
        }

        /** Render the close X button. */
        public Builder dismissable() {
            this.dismissable = true;
            return this;
        }

        /** Body text (escaped). */
        public Builder body(String body) {
            this.body = body;
            return this;
        }

        /** Plain footer text. */
        public Builder footerText(String footerText) {
            this.footerText = footerText;
            return this;
        }

        /** Footer action button; closes=true wires @click="open = false". */
        public Builder footerButton(String text, String modifiers, boolean closes) {
            footerButtons.add(new FooterButton(Objects.requireNonNull(text, "text"),
                    Objects.requireNonNull(modifiers, "modifiers"), closes));
            return this;
        }

        public Popover build() {
            Objects.requireNonNull(triggerText, "trigger");
            Objects.requireNonNull(body, "body");
            if (title == null && ariaLabel == null) {
                throw new IllegalStateException("A popover needs a title or an ariaLabel");
            }
            return new Popover(this);
        }
    }
}
