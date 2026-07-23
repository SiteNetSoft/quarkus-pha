package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Switch component ({@code pf-v6-c-switch}).
 *
 * <pre>
 * Switch.of("sw-basic").label("Enable notifications").build()
 * Switch.of("sw-checked").label("Already on").checked().build()
 * Switch.of("sw-plain").ariaLabel("Enable feature").checked().build()   // no visible label
 * </pre>
 *
 * <p>The input id is derived as {@code {id}-field} for the label's {@code for=}
 * wiring. Provide either a visible {@code label} or an {@code ariaLabel}.
 */
@TemplateData
public final class Switch {

    private final String id;
    private final String label;
    private final String ariaLabel;
    private final boolean checked;
    private final boolean disabled;
    private final boolean reversed;
    private final boolean checkIcon;

    private Switch(Builder b) {
        this.id = b.id;
        this.label = b.label;
        this.ariaLabel = b.ariaLabel;
        this.checked = b.checked;
        this.disabled = b.disabled;
        this.reversed = b.reversed;
        this.checkIcon = b.checkIcon;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String label() {
        return label;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isCheckIcon() {
        return checkIcon;
    }

    public boolean isReversed() {
        return reversed;
    }

    public static final class Builder {
        private String id;
        private String label;
        private String ariaLabel;
        private boolean checked;
        private boolean disabled;
        private boolean reversed;
        private boolean checkIcon;

        private Builder() {
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        /** Accessible name when there is no visible label. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder checked() {
            this.checked = true;
            return this;
        }

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        /** Label before the toggle (pf-m-reverse). */
        /** The "label and check icon" variant — check glyph inside the toggle. */
        public Builder checkIcon() {
            this.checkIcon = true;
            return this;
        }

        public Builder reversed() {
            this.reversed = true;
            return this;
        }

        public Switch build() {
            if (label == null && ariaLabel == null) {
                throw new IllegalStateException("A switch needs a label or an ariaLabel");
            }
            return new Switch(this);
        }
    }
}
