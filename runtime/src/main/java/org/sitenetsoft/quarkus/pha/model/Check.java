package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Check (checkbox) component ({@code pf-v6-c-check}).
 *
 * <pre>
 * Check.of("cb-basic").label("Accept terms").build()
 * Check.of("cb-req").label("I agree to the terms").required().build()
 * Check.of("cb-rev").label("Label first").reversed().build()
 * Check.standalone("cb-standalone", "Select all rows").build()
 * </pre>
 *
 * <p>Ids derive as in the shell: {@code {id}-wrap} on the wrapper, the input
 * carries {@code id} itself, {@code {id}-description} wires aria-describedby.
 */
@TemplateData
public final class Check {

    private final String id;
    private final String label;
    private final String ariaLabel;
    private final String description;
    private final String body;
    private final boolean checked;
    private final boolean disabled;
    private final boolean required;
    private final boolean reversed;
    private final boolean standalone;

    private Check(Builder b) {
        this.id = b.id;
        this.label = b.label;
        this.ariaLabel = b.ariaLabel;
        this.description = b.description;
        this.body = b.body;
        this.checked = b.checked;
        this.disabled = b.disabled;
        this.required = b.required;
        this.reversed = b.reversed;
        this.standalone = b.standalone;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    /** No visible label — pair with an external accessible name. */
    public static Builder standalone(String id, String ariaLabel) {
        Builder b = of(id);
        b.standalone = true;
        b.ariaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
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

    public String description() {
        return description;
    }

    public String bodyText() {
        return body;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isReversed() {
        return reversed;
    }

    public boolean isStandalone() {
        return standalone;
    }

    public static final class Builder {
        private String id;
        private String label;
        private String ariaLabel;
        private String description;
        private String body;
        private boolean checked;
        private boolean disabled;
        private boolean required;
        private boolean reversed;
        private boolean standalone;

        private Builder() {
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /** Extra body content under the description — fine print or links. */
        public Builder body(String body) {
            this.body = body;
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

        public Builder required() {
            this.required = true;
            return this;
        }

        /** Label before the input (DOM order — PF v6 has no modifier class). */
        public Builder reversed() {
            this.reversed = true;
            return this;
        }

        public Check build() {
            if (!standalone && label == null) {
                throw new IllegalStateException("A check needs a label (or use Check.standalone)");
            }
            return new Check(this);
        }
    }
}
