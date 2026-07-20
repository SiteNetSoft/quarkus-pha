package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Form select component ({@code pf-v6-c-form-control}
 * wrapping a {@code <select>}).
 *
 * <pre>
 * FormSelect.of("fs-basic").options("""
 *     &lt;option value=""&gt;Select…&lt;/option&gt;
 *     &lt;option value="mr"&gt;Mr&lt;/option&gt;""").build()
 * FormSelect.of("fs-error").validated("error").options(...).build()
 * </pre>
 *
 * <p>Options are raw HTML ({@code <option>}/{@code <optgroup>} elements). The
 * select id derives as {@code {id}-field}.
 */
@TemplateData
public final class FormSelect {

    private final String id;
    private final String optionsHtml;
    private final String ariaLabel;
    private final String validated;
    private final boolean disabled;
    private final boolean required;

    private FormSelect(Builder b) {
        this.id = b.id;
        this.optionsHtml = b.optionsHtml;
        this.ariaLabel = b.ariaLabel;
        this.validated = b.validated;
        this.disabled = b.disabled;
        this.required = b.required;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String optionsHtml() {
        return optionsHtml;
    }

    public String ariaLabel() {
        return ariaLabel != null ? ariaLabel : "Select";
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

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isRequired() {
        return required;
    }

    public static final class Builder {
        private String id;
        private String optionsHtml;
        private String ariaLabel;
        private String validated;
        private boolean disabled;
        private boolean required;

        private Builder() {
        }

        /** Raw HTML: one or more option/optgroup elements. */
        public Builder options(String optionsHtml) {
            this.optionsHtml = optionsHtml;
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

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        public Builder required() {
            this.required = true;
            return this;
        }

        public FormSelect build() {
            if (optionsHtml == null) {
                throw new IllegalStateException("A form select needs options(...)");
            }
            return new FormSelect(this);
        }
    }
}
