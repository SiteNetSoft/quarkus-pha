package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * One step of a {@link ProgressStepper} — a title with optional description,
 * status, icon and in-process spinner.
 *
 * <pre>
 * ProgressStepperStep.of("Build").description("Compiling sources…").asCurrent()
 * </pre>
 *
 * <p>Statuses map to the PF step modifiers ({@code pf-m-success},
 * {@code pf-m-danger}, {@code pf-m-warning}, {@code pf-m-pending},
 * {@code pf-m-custom}); {@link #asCurrent()} marks the active step
 * ({@code aria-current="step"}, plus {@code pf-m-current} when no other
 * status is set). Success, danger and warning steps get their stock status
 * icon unless {@link #icon(String)} overrides it.
 */
@TemplateData
public final class ProgressStepperStep {

    private final String title;
    private final String description;
    private final String status;
    private final boolean current;
    private final String icon;
    private final String spinnerLabel;

    private ProgressStepperStep(String title, String description, String status,
                                boolean current, String icon, String spinnerLabel) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.current = current;
        this.icon = icon;
        this.spinnerLabel = spinnerLabel;
    }

    public static ProgressStepperStep of(String title) {
        return new ProgressStepperStep(Objects.requireNonNull(title, "title"), null, null, false, null, null);
    }

    public ProgressStepperStep description(String description) {
        return new ProgressStepperStep(title, description, status, current, icon, spinnerLabel);
    }

    /** Completed step ({@code pf-m-success}, stock check icon). */
    public ProgressStepperStep asSuccess() {
        return new ProgressStepperStep(title, description, "success", current, icon, spinnerLabel);
    }

    /** Failed step ({@code pf-m-danger}, stock exclamation icon). */
    public ProgressStepperStep asDanger() {
        return new ProgressStepperStep(title, description, "danger", current, icon, spinnerLabel);
    }

    /** Step that completed with warnings ({@code pf-m-warning}, stock triangle icon). */
    public ProgressStepperStep asWarning() {
        return new ProgressStepperStep(title, description, "warning", current, icon, spinnerLabel);
    }

    /** Explicitly pending step ({@code pf-m-pending}). */
    public ProgressStepperStep asPending() {
        return new ProgressStepperStep(title, description, "pending", current, icon, spinnerLabel);
    }

    /** Custom-icon step ({@code pf-m-custom}); pair with {@link #icon(String)}. */
    public ProgressStepperStep asCustom() {
        return new ProgressStepperStep(title, description, "custom", current, icon, spinnerLabel);
    }

    /** The active step: {@code aria-current="step"}, and {@code pf-m-current} unless another status is set. */
    public ProgressStepperStep asCurrent() {
        return new ProgressStepperStep(title, description, status, true, icon, spinnerLabel);
    }

    /** Step icon, e.g. {@code "fa:rocket"} — overrides the status default. */
    public ProgressStepperStep icon(String icon) {
        return new ProgressStepperStep(title, description, status, current, icon, spinnerLabel);
    }

    /** Animated xs spinner as the step icon (an in-process step), with its accessible label. */
    public ProgressStepperStep spinner(String label) {
        return new ProgressStepperStep(title, description, status, current, icon,
                Objects.requireNonNull(label, "label"));
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public boolean isCurrent() {
        return current;
    }

    public boolean isInProcess() {
        return spinnerLabel != null;
    }

    public String spinnerLabel() {
        return spinnerLabel;
    }

    /** Step modifier class, or null for a plain step. */
    public String statusClass() {
        if (status != null) {
            return "pf-m-" + status;
        }
        return current ? "pf-m-current" : null;
    }

    /** Icon resolved for rendering: the explicit icon, else the status default, else none. */
    public String displayIcon() {
        if (icon != null) {
            return icon;
        }
        if (status == null) {
            return null;
        }
        return switch (status) {
            case "success" -> "fa:circle-check";
            case "danger" -> "fa:exclamation-circle";
            case "warning" -> "fa:exclamation-triangle";
            default -> null;
        };
    }
}
