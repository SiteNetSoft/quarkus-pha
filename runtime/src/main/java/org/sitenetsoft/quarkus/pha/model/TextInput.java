package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Text input component ({@code pf-v6-c-form-control}).
 *
 * <pre>
 * TextInput.of("ti-basic").placeholder("Enter value").build()
 * TextInput.of("ti-invalid").type("email").value("not-an-email").validated("error").build()
 * TextInput.of("ti-search").type("search").placeholder("Search").icon("fa:magnifying-glass").build()
 * </pre>
 *
 * <p>The input id derives as {@code {id}-field}; the accessible name falls back
 * ariaLabel → placeholder → value → id, matching the param shell.
 */
@TemplateData
public final class TextInput {

    private final String id;
    private final String type;
    private final String placeholder;
    private final String value;
    private final String ariaLabel;
    private final String validated;
    private final String iconName;
    private final boolean disabled;
    private final boolean readonly;
    private final boolean required;
    private final boolean expanded;

    private TextInput(Builder b) {
        this.id = b.id;
        this.type = b.type;
        this.placeholder = b.placeholder;
        this.value = b.value;
        this.ariaLabel = b.ariaLabel;
        this.validated = b.validated;
        this.iconName = b.iconName;
        this.disabled = b.disabled;
        this.readonly = b.readonly;
        this.required = b.required;
        this.expanded = b.expanded;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String type() {
        return type != null ? type : "text";
    }

    public String placeholder() {
        return placeholder;
    }

    public String value() {
        return value;
    }

    /** ariaLabel → placeholder → value → id, matching the param shell. */
    public String ariaLabelResolved() {
        if (ariaLabel != null) {
            return ariaLabel;
        }
        if (placeholder != null) {
            return placeholder;
        }
        if (value != null) {
            return value;
        }
        return id;
    }

    public String validated() {
        return validated;
    }

    public boolean isError() {
        return "error".equals(validated);
    }

    public boolean isSuccess() {
        return "success".equals(validated);
    }

    public boolean isWarning() {
        return "warning".equals(validated);
    }

    public String iconName() {
        return iconName;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public static final class Builder {
        private String id;
        private String type;
        private String placeholder;
        private String value;
        private String ariaLabel;
        private String validated;
        private String iconName;
        private boolean disabled;
        private boolean readonly;
        private boolean required;
        private boolean expanded;

        private Builder() {
        }

        /** text (default) | password | email | number | tel | url | search | date | ... */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder placeholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** success | warning | error — emits pf-m-* and the status icon. */
        public Builder validated(String validated) {
            this.validated = validated;
            return this;
        }

        /** Prefix utility icon as "set:slug" (pf-m-icon). */
        public Builder icon(String iconName) {
            this.iconName = iconName;
            return this;
        }

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        public Builder readonly() {
            this.readonly = true;
            return this;
        }

        public Builder required() {
            this.required = true;
            return this;
        }

        public Builder expanded() {
            this.expanded = true;
            return this;
        }

        public TextInput build() {
            return new TextInput(this);
        }
    }
}
