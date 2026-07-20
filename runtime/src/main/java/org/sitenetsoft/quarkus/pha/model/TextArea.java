package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Text area component ({@code pf-v6-c-form-control pf-m-textarea}).
 *
 * <pre>
 * TextArea.of("ta-basic").placeholder("Enter your message…").rows(4).build()
 * TextArea.of("ta-resize").resize("vertical").rows(3).build()
 * TextArea.of("ta-invalid").rows(3).value("Too short.").validated("error").build()
 * </pre>
 *
 * <p>The textarea id derives as {@code {id}-field}; the accessible name falls
 * back ariaLabel → placeholder → value → id, matching the param shell.
 */
@TemplateData
public final class TextArea {

    private final String id;
    private final String placeholder;
    private final String value;
    private final String ariaLabel;
    private final String validated;
    private final String resize;
    private final Integer rows;
    private final boolean disabled;
    private final boolean readonly;
    private final boolean required;
    private final boolean expanded;

    private TextArea(Builder b) {
        this.id = b.id;
        this.placeholder = b.placeholder;
        this.value = b.value;
        this.ariaLabel = b.ariaLabel;
        this.validated = b.validated;
        this.resize = b.resize;
        this.rows = b.rows;
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

    public String resize() {
        return resize;
    }

    public Integer rows() {
        return rows;
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
        private String placeholder;
        private String value;
        private String ariaLabel;
        private String validated;
        private String resize;
        private Integer rows;
        private boolean disabled;
        private boolean readonly;
        private boolean required;
        private boolean expanded;

        private Builder() {
        }

        public Builder placeholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        /** Initial textarea content. */
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

        /** vertical | horizontal | both — emits pf-m-resize-*. */
        public Builder resize(String resize) {
            this.resize = resize;
            return this;
        }

        public Builder rows(int rows) {
            this.rows = rows;
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

        public TextArea build() {
            return new TextArea(this);
        }
    }
}
