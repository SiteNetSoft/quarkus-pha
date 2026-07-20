package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Progress component ({@code pf-v6-c-progress}).
 *
 * <pre>
 * Progress.of("prog-basic", 33).title("Basic progress").build()
 * Progress.of("prog-success", 100).title("Backup complete").variant("success").build()
 * Progress.of("prog-step", 2).range(0, 5).title("Onboarding")
 *     .label("Step 2 of 5").valueText("Step 2 of 5").build()
 * </pre>
 *
 * <p>Covers the runtime template's surface: title (optionally truncated),
 * percentage or custom measure at the top / outside / inside / hidden,
 * success/warning/danger variants with their status icon, sm/lg sizes,
 * single-line layout, and helper text below the bar. Helper text renders
 * with a derived {@code {id}-helper} element id wired into the bar's
 * {@code aria-describedby}.
 */
@TemplateData
public final class Progress {

    private final String id;
    private final int value;
    private final Integer min;
    private final Integer max;
    private final String title;
    private final boolean truncateTitle;
    private final String label;
    private final String valueText;
    private final String helperText;
    private final String size;
    private final String variant;
    private final String measureLocation;
    private final boolean singleline;
    private final String ariaLabel;

    private Progress(Builder b) {
        this.id = b.id;
        this.value = b.value;
        this.min = b.min;
        this.max = b.max;
        this.title = b.title;
        this.truncateTitle = b.truncateTitle;
        this.label = b.label;
        this.valueText = b.valueText;
        this.helperText = b.helperText;
        this.size = b.size;
        this.variant = b.variant;
        this.measureLocation = b.measureLocation;
        this.singleline = b.singleline;
        this.ariaLabel = b.ariaLabel;
    }

    /** Root id (helper/description ids derive from it) and the current value. */
    public static Builder of(String id, int value) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.value = value;
        return b;
    }

    public String id() {
        return id;
    }

    public int value() {
        return value;
    }

    public int minValue() {
        return min != null ? min : 0;
    }

    public int maxValue() {
        return max != null ? max : 100;
    }

    public String title() {
        return title;
    }

    /** Indicator width as a true percentage of the min..max range. */
    public int percent() {
        int lo = minValue(), hi = maxValue();
        if (hi <= lo) {
            return 0;
        }
        return Math.round((value - lo) * 100f / (hi - lo));
    }

    public boolean isTruncateTitle() {
        return truncateTitle;
    }

    /** Visible measure text: the custom label, else "{value}%". */
    public String measure() {
        return label != null ? label : value + "%";
    }

    public String valueText() {
        return valueText;
    }

    public String helperText() {
        return helperText;
    }

    public String helperId() {
        return id + "-helper";
    }

    public String descriptionId() {
        return id + "-description";
    }

    public String size() {
        return size;
    }

    public String variant() {
        return variant;
    }

    /** Stock status icon for the variant. */
    public String statusIcon() {
        if (variant == null) {
            return null;
        }
        return switch (variant) {
            case "success" -> "fa:circle-check";
            case "warning" -> "fa:triangle-exclamation";
            case "danger" -> "fa:circle-exclamation";
            default -> null;
        };
    }

    public String measureLocation() {
        return measureLocation;
    }

    public boolean isMeasureInside() {
        return "inside".equals(measureLocation);
    }

    public boolean isMeasureOutside() {
        return "outside".equals(measureLocation);
    }

    /** True when the measure renders in the status row (top/outside placements). */
    public boolean isMeasureInStatus() {
        return !("inside".equals(measureLocation) || "none".equals(measureLocation));
    }

    public boolean isSingleline() {
        return singleline;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public static final class Builder {

        private String id;
        private int value;
        private Integer min;
        private Integer max;
        private String title;
        private boolean truncateTitle;
        private String label;
        private String valueText;
        private String helperText;
        private String size;
        private String variant;
        private String measureLocation;
        private boolean singleline;
        private String ariaLabel;

        private Builder() {
        }

        /** Heading line above the bar (also labels the bar for AT). */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /** Ellipsize a long title instead of wrapping. */
        public Builder asTruncatedTitle() {
            this.truncateTitle = true;
            return this;
        }

        /** Non-percentage bounds, e.g. {@code range(0, 5)} for step progress. */
        public Builder range(int min, int max) {
            this.min = min;
            this.max = max;
            return this;
        }

        /** Visible measure text replacing the percentage, e.g. {@code "Step 2 of 5"}. */
        public Builder label(String label) {
            this.label = label;
            return this;
        }

        /** aria-valuetext for non-percentage values. */
        public Builder valueText(String valueText) {
            this.valueText = valueText;
            return this;
        }

        /** Context line below the bar, auto-wired into aria-describedby. */
        public Builder helperText(String helperText) {
            this.helperText = helperText;
            return this;
        }

        /** sm | lg. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        /** success | warning | danger — status color plus its stock icon. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        /** Percentage rendered on the filled indicator (best with size "lg"). */
        public Builder measureInside() {
            this.measureLocation = "inside";
            return this;
        }

        /** Percentage rendered to the right of the bar. */
        public Builder measureOutside() {
            this.measureLocation = "outside";
            return this;
        }

        /** Hide the measure text; the bar still announces aria-valuenow. */
        public Builder noMeasure() {
            this.measureLocation = "none";
            return this;
        }

        /** Compact one-row layout; pair with {@link #ariaLabel(String)} instead of a title. */
        public Builder asSingleline() {
            this.singleline = true;
            return this;
        }

        /** Accessible name for the bar when there is no title. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Progress build() {
            return new Progress(this);
        }
    }
}
