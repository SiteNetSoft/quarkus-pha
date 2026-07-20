package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Typed model for the Inline edit component ({@code pf-v6-c-inline-edit}).
 *
 * <pre>
 * InlineEdit.of("ie-basic", "Click the pencil to edit me").build()
 * InlineEdit.labeled("ie-label", "Title", "Senior Engineer", "Edit title")
 *     .style("max-width: 28rem").build()
 * InlineEdit.multiple("ie-multiple").style("max-width: 32rem")
 *     .field("Name", "Jane Smith").field("Team", "Platform").field("Role", "Editor").build()
 * InlineEdit.validated("ie-validated", "Display name", "Type to validate…",
 *     "Display name is required.").style("max-width: 28rem").build()
 * InlineEdit.freeForm("ie-free-form", "Free form text — click and type to edit.",
 *     "Editable free form text").build()
 * </pre>
 *
 * <p>Five shapes; the editing/value Alpine is generated at build time.
 * Multi-field state vars derive from the lowercased label.
 */
@TemplateData
public final class InlineEdit {

    /** One field of the whole-row (multiple) shape. */
    @TemplateData
    public static final class Field {
        private final String label;
        private final String stateVar;

        private Field(String label, String stateVar) {
            this.label = label;
            this.stateVar = stateVar;
        }

        public String label() {
            return label;
        }

        public String stateVar() {
            return stateVar;
        }
    }

    private final String id;
    private final String shape;
    private final String value;
    private final String label;
    private final String editAriaLabel;
    private final String placeholder;
    private final String errorText;
    private final String style;
    private final List<Field> fields;
    private final String xData;

    private InlineEdit(Builder b) {
        this.id = b.id;
        this.shape = b.shape;
        this.value = b.value;
        this.label = b.label;
        this.editAriaLabel = b.editAriaLabel;
        this.placeholder = b.placeholder;
        this.errorText = b.errorText;
        this.style = b.style;
        this.fields = List.copyOf(b.fields);
        if ("multiple".equals(shape)) {
            StringBuilder sb = new StringBuilder("{ editing: false");
            for (int i = 0; i < b.fields.size(); i++) {
                sb.append(", ").append(b.fields.get(i).stateVar).append(": '")
                        .append(b.fieldValues.get(i).replace("'", "\\'")).append("'");
            }
            this.xData = sb.append(" }").toString();
        } else if ("basic".equals(shape) || "labeled".equals(shape)) {
            this.xData = "{ editing: false, val: '" + (value != null ? value.replace("'", "\\'") : "") + "' }";
        } else {
            this.xData = null;
        }
    }

    public static Builder of(String id, String value) {
        Builder b = new Builder("basic", id);
        b.value = Objects.requireNonNull(value, "value");
        return b;
    }

    /** Labeled single value with pencil → input + icon save/cancel. */
    public static Builder labeled(String id, String label, String value, String editAriaLabel) {
        Builder b = new Builder("labeled", id);
        b.label = Objects.requireNonNull(label, "label");
        b.value = Objects.requireNonNull(value, "value");
        b.editAriaLabel = Objects.requireNonNull(editAriaLabel, "editAriaLabel");
        return b;
    }

    /** Whole-row edit: one Edit toggle, N fields, footer Save/Cancel. */
    public static Builder multiple(String id) {
        return new Builder("multiple", id);
    }

    /** Always-editing required field with as-you-type validation. */
    public static Builder validated(String id, String label, String placeholder, String errorText) {
        Builder b = new Builder("validated", id);
        b.label = Objects.requireNonNull(label, "label");
        b.placeholder = Objects.requireNonNull(placeholder, "placeholder");
        b.errorText = Objects.requireNonNull(errorText, "errorText");
        return b;
    }

    /** Bare contenteditable free-form text block. */
    public static Builder freeForm(String id, String text, String ariaLabel) {
        Builder b = new Builder("freeform", id);
        b.value = Objects.requireNonNull(text, "text");
        b.editAriaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return b;
    }

    public String id() {
        return id;
    }

    public boolean isBasic() {
        return "basic".equals(shape);
    }

    public boolean isLabeled() {
        return "labeled".equals(shape);
    }

    public boolean isMultiple() {
        return "multiple".equals(shape);
    }

    public boolean isValidated() {
        return "validated".equals(shape);
    }

    public boolean isFreeForm() {
        return "freeform".equals(shape);
    }

    public String value() {
        return value;
    }

    public String label() {
        return label;
    }

    public String editAriaLabel() {
        return editAriaLabel;
    }

    public String placeholder() {
        return placeholder;
    }

    public String errorText() {
        return errorText;
    }

    public String style() {
        return style;
    }

    public List<Field> fields() {
        return fields;
    }

    public String xData() {
        return xData;
    }

    public static final class Builder {
        private final String shape;
        private String id;
        private String value;
        private String label;
        private String editAriaLabel;
        private String placeholder;
        private String errorText;
        private String style;
        private final List<Field> fields = new ArrayList<>();
        private final List<String> fieldValues = new ArrayList<>();

        private Builder(String shape, String id) {
            this.shape = shape;
            this.id = Objects.requireNonNull(id, "id");
        }

        /** Inline style on the root. */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        /** Multiple shape: one field; the state var is the lowercased label. */
        public Builder field(String label, String initialValue) {
            fields.add(new Field(Objects.requireNonNull(label, "label"),
                    label.toLowerCase(Locale.ROOT).replace(" ", "")));
            fieldValues.add(Objects.requireNonNull(initialValue, "initialValue"));
            return this;
        }

        public InlineEdit build() {
            if ("multiple".equals(shape) && fields.isEmpty()) {
                throw new IllegalStateException("A multiple inline edit needs at least one field");
            }
            return new InlineEdit(this);
        }
    }
}
