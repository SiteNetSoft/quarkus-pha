package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Radio component ({@code pf-v6-c-radio}).
 *
 * <pre>
 * Radio.of("r-one", "group").label("Option one").checked().build()
 * Radio.of("r-desc", "plans").label("Standard").description("3 GB storage.").build()
 * Radio.standalone("r-solo", "rows").build()
 * </pre>
 *
 * <p>Radios sharing a {@code name} form one group. The input id derives as
 * {@code {id}-field} for the label's {@code for=} wiring.
 */
@TemplateData
public final class Radio {

    private final String id;
    private final String name;
    private final String label;
    private final String ariaLabel;
    private final String description;
    private final String body;
    private final boolean checked;
    private final boolean disabled;
    private final boolean standalone;

    private Radio(Builder b) {
        this.id = b.id;
        this.name = b.name;
        this.label = b.label;
        this.ariaLabel = b.ariaLabel;
        this.description = b.description;
        this.body = b.body;
        this.checked = b.checked;
        this.disabled = b.disabled;
        this.standalone = b.standalone;
    }

    public static Builder of(String id, String name) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.name = Objects.requireNonNull(name, "name");
        return b;
    }

    /** No visible label — named via aria-label (falls back to the group name). */
    public static Builder standalone(String id, String name) {
        Builder b = of(id, name);
        b.standalone = true;
        return b;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String label() {
        return label;
    }

    /** Standalone accessible name — explicit ariaLabel or the group name. */
    public String ariaLabelResolved() {
        return ariaLabel != null ? ariaLabel : name;
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

    public boolean isStandalone() {
        return standalone;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String label;
        private String ariaLabel;
        private String description;
        private String body;
        private boolean checked;
        private boolean disabled;
        private boolean standalone;

        private Builder() {
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /** Extra body content — links or fine print. */
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

        public Radio build() {
            if (!standalone && label == null) {
                throw new IllegalStateException("A radio needs a label (or use Radio.standalone)");
            }
            return new Radio(this);
        }
    }
}
