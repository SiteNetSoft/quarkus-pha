package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Tooltip component ({@code pf-v6-c-tooltip}).
 *
 * <pre>
 * Tooltip.of("tt-basic")
 *     .trigger(Button.of("Hover or focus me").variant("secondary").build())
 *     .tip("I'm a tooltip").build()
 * Tooltip.of("tt-icon")
 *     .trigger(Button.icon("fa:circle-info", "More information").variant("plain").build())
 *     .tip("More information about this field").build()
 * </pre>
 *
 * <p>The trigger is a composed {@link Button} model; the tip is plain text.
 * Needs /web/js/alpine/components/tooltip.js and /web/css/components/tooltip.css
 * on the host page. Position accepts the 4 sides plus the 8 diagonal variants
 * (top-left, right-bottom, ...).
 */
@TemplateData
public final class Tooltip {

    private final String id;
    private final String position;
    private final boolean textAlignLeft;
    private final String ariaLive;
    private final Button trigger;
    private final String tipText;

    private Tooltip(Builder b) {
        this.id = b.id;
        this.position = b.position;
        this.textAlignLeft = b.textAlignLeft;
        this.ariaLive = b.ariaLive;
        this.trigger = b.trigger;
        this.tipText = b.tipText;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String position() {
        return position != null ? position : "top";
    }

    public boolean isTextAlignLeft() {
        return textAlignLeft;
    }

    public String ariaLive() {
        return ariaLive;
    }

    public Button trigger() {
        return trigger;
    }

    public String tipText() {
        return tipText;
    }

    public static final class Builder {
        private String id;
        private String position;
        private boolean textAlignLeft;
        private String ariaLive;
        private Button trigger;
        private String tipText;

        private Builder() {
        }

        /** top (default) | bottom | left | right | top-left | ... | right-bottom. */
        public Builder position(String position) {
            this.position = position;
            return this;
        }

        /** Ragged-right text for multi-line tips (pf-m-text-align-left). */
        public Builder textAlignLeft() {
            this.textAlignLeft = true;
            return this;
        }

        /** aria-live on the tip — "polite" when tip content changes while open. */
        public Builder ariaLive(String ariaLive) {
            this.ariaLive = ariaLive;
            return this;
        }

        /** The hover/focus target. */
        public Builder trigger(Button trigger) {
            this.trigger = trigger;
            return this;
        }

        /** Tooltip text (escaped). */
        public Builder tip(String tipText) {
            this.tipText = tipText;
            return this;
        }

        public Tooltip build() {
            Objects.requireNonNull(trigger, "trigger");
            Objects.requireNonNull(tipText, "tip");
            return new Tooltip(this);
        }
    }
}
