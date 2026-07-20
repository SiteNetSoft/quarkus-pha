package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Text input group component ({@code pf-v6-c-text-input-group}).
 *
 * <pre>
 * TextInputGroup.of("tig-basic").placeholder("Type here…").build()
 * TextInputGroup.of("tig-hint").value("apples").hint("appleseed").ariaLabel("Autocomplete").build()
 * TextInputGroup.of("tig-ok").value("Valid value").status("success").ariaLabel("Success input").build()
 * </pre>
 *
 * <p>{@code hint} renders PF's autocomplete ghost (a disabled pf-m-hint input
 * behind the real one); {@code status} adds pf-m-{status} plus the trailing
 * status icon. Live utilities (clear buttons, filter chips) stay hand-written.
 */
@TemplateData
public final class TextInputGroup {

    private final String id;
    private final String placeholder;
    private final String value;
    private final String ariaLabel;
    private final String hintText;
    private final String iconName;
    private final String status;
    private final boolean disabled;
    private final boolean plain;

    private TextInputGroup(Builder b) {
        this.id = b.id;
        this.placeholder = b.placeholder;
        this.value = b.value;
        this.ariaLabel = b.ariaLabel;
        this.hintText = b.hintText;
        this.iconName = b.iconName;
        this.status = b.status;
        this.disabled = b.disabled;
        this.plain = b.plain;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String placeholder() {
        return placeholder;
    }

    public String value() {
        return value;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public String hintText() {
        return hintText;
    }

    public String iconName() {
        return iconName;
    }

    public String status() {
        return status;
    }

    public boolean isError() {
        return "error".equals(status);
    }

    /** Trailing status icon for the status flavour. */
    public String statusIcon() {
        if (status == null) {
            return null;
        }
        return switch (status) {
            case "success" -> "fa:circle-check";
            case "warning" -> "fa:triangle-exclamation";
            case "error" -> "fa:circle-exclamation";
            default -> null;
        };
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isPlain() {
        return plain;
    }

    public static final class Builder {
        private String id;
        private String placeholder;
        private String value;
        private String ariaLabel;
        private String hintText;
        private String iconName;
        private String status;
        private boolean disabled;
        private boolean plain;

        private Builder() {
        }

        public Builder placeholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        /** Prefilled text. */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Autocomplete ghost text (disabled pf-m-hint input behind the real one). */
        public Builder hint(String hintText) {
            this.hintText = hintText;
            return this;
        }

        /** Leading icon as "set:slug" (pf-m-icon on __main). */
        public Builder icon(String iconName) {
            this.iconName = iconName;
            return this;
        }

        /** success | warning | error — pf-m-{status} on the root + trailing status icon. */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        /** No own border/background — only inside an ancestor that provides them. */
        public Builder plain() {
            this.plain = true;
            return this;
        }

        public TextInputGroup build() {
            return new TextInputGroup(this);
        }
    }
}
