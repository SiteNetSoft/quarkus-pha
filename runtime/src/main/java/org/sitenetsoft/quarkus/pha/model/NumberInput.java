package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Number input component ({@code pf-v6-c-number-input}).
 *
 * <pre>
 * NumberInput.of("ni-basic", 5).build()
 * NumberInput.of("ni-bounded", 3).min(0).max(10).build()
 * NumberInput.of("ni-step", 90).step(3).build()
 * NumberInput.of("ni-clamped", 6).step(3).min(0).max(12).build()
 * NumberInput.of("ni-status", 1).statusFollowsValue().build()
 * </pre>
 *
 * <p>The Alpine wiring is generated at build time. Expressions are canonical
 * minimal forms: without a bound the click is {@code val = val - step} (no
 * no-op Infinity clamp) and the {@code :disabled} binding is omitted entirely.
 * {@code statusFollowsValue()} colors the form-control by sign (error &lt; 0,
 * warning = 0, success &gt; 0).
 */
@TemplateData
public final class NumberInput {

    private final String id;
    private final long value;
    private final Long min;
    private final Long max;
    private final long step;
    private final String unit;
    private final String ariaLabel;
    private final boolean disabled;
    private final Integer widthCh;
    private final boolean status;

    private NumberInput(Builder b) {
        this.id = b.id;
        this.value = b.value;
        this.min = b.min;
        this.max = b.max;
        this.step = b.step;
        this.unit = b.unit;
        this.ariaLabel = b.ariaLabel;
        this.disabled = b.disabled;
        this.widthCh = b.widthCh;
        this.status = b.status;
    }

    public static Builder of(String id, long value) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.value = value;
        return b;
    }

    public String id() {
        return id;
    }

    public String xData() {
        return "{ val: " + value + " }";
    }

    public String minusClick() {
        if (min != null) {
            return "val = Math.max(val - " + step + ", " + min + ")";
        }
        return "val = val - " + step;
    }

    public String plusClick() {
        if (max != null) {
            return "val = Math.min(val + " + step + ", " + max + ")";
        }
        return "val = val + " + step;
    }

    /** Null when the button is never disabled (binding omitted). */
    public String minusDisabledExpr() {
        if (disabled) {
            return "true";
        }
        if (min != null) {
            return "val <= " + min;
        }
        return null;
    }

    public String plusDisabledExpr() {
        if (disabled) {
            return "true";
        }
        if (max != null) {
            return "val >= " + max;
        }
        return null;
    }

    public String minAttr() {
        return min != null ? String.valueOf(min) : null;
    }

    public String maxAttr() {
        return max != null ? String.valueOf(max) : null;
    }

    public String unit() {
        return unit;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String inputStyle() {
        return widthCh != null ? "width: " + widthCh + "ch" : null;
    }

    public boolean isStatus() {
        return status;
    }

    public String statusClassExpr() {
        return "val < 0 ? 'pf-m-error' : (val === 0 ? 'pf-m-warning' : 'pf-m-success')";
    }

    /** ariaLabel → "{unit} count" → "Number input", matching the param shell. */
    public String inputAriaLabel() {
        if (ariaLabel != null) {
            return ariaLabel;
        }
        if (unit != null) {
            return unit + " count";
        }
        return "Number input";
    }

    public static final class Builder {
        private String id;
        private long value;
        private Long min;
        private Long max;
        private long step = 1;
        private String unit;
        private String ariaLabel;
        private boolean disabled;
        private Integer widthCh;
        private boolean status;

        private Builder() {
        }

        public Builder min(long min) {
            this.min = min;
            return this;
        }

        public Builder max(long max) {
            this.max = max;
            return this;
        }

        /** Increment/decrement step (default 1). */
        public Builder step(long step) {
            this.step = step;
            return this;
        }

        /** Unit label after the plus button (e.g. "hours"). */
        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        /** Inline input width in ch units (varying-sizes pattern). */
        public Builder widthCh(int widthCh) {
            this.widthCh = widthCh;
            return this;
        }

        /** Color the control by value sign: error &lt; 0, warning = 0, success &gt; 0. */
        public Builder statusFollowsValue() {
            this.status = true;
            return this;
        }

        public NumberInput build() {
            return new NumberInput(this);
        }
    }
}
