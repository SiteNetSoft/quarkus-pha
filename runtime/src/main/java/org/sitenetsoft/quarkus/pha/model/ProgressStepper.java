package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Progress stepper component ({@code pf-v6-c-progress-stepper}).
 *
 * <p>Describe the steps as data and the runtime template renders the full
 * PatternFly markup — statuses, stock or custom icons, descriptions and the
 * animated in-process spinner:
 *
 * <pre>
 * ProgressStepper stepper = ProgressStepper.builder()
 *     .ariaLabel("Progress steps")
 *     .step(ProgressStepperStep.of("First step").description("Completed").asSuccess())
 *     .step(ProgressStepperStep.of("Second step").description("In progress").asCurrent())
 *     .step(ProgressStepperStep.of("Third step").description("Pending"))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/feedback/progress-stepper stepper=stepper /}</code>
 *
 * <p>Layout via {@link Builder#vertical()}, {@link Builder#compact()},
 * {@link Builder#center()}; responsive breakpoint combos
 * (e.g. {@code pf-m-vertical-on-lg pf-m-horizontal-on-2xl}) go through
 * {@link Builder#modifiers(String)} verbatim.
 */
@TemplateData
public final class ProgressStepper {

    private final String id;
    private final String ariaLabel;
    private final boolean vertical;
    private final boolean compact;
    private final boolean center;
    private final String modifiers;
    private final List<ProgressStepperStep> steps;

    private ProgressStepper(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.vertical = b.vertical;
        this.compact = b.compact;
        this.center = b.center;
        this.modifiers = b.modifiers;
        this.steps = List.copyOf(b.steps);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isCenter() {
        return center;
    }

    public String modifiers() {
        return modifiers;
    }

    public List<ProgressStepperStep> steps() {
        return steps;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel;
        private boolean vertical;
        private boolean compact;
        private boolean center;
        private String modifiers;
        private final List<ProgressStepperStep> steps = new ArrayList<>();

        private Builder() {
        }

        /** DOM id on the root {@code <ol>}. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Accessible name of the stepper list. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        public Builder compact() {
            this.compact = true;
            return this;
        }

        /** Center-aligned steps. */
        public Builder center() {
            this.center = true;
            return this;
        }

        /** Extra pf-m-* classes appended verbatim, e.g. responsive {@code pf-m-vertical-on-lg pf-m-horizontal-on-2xl}. */
        public Builder modifiers(String modifiers) {
            this.modifiers = Objects.requireNonNull(modifiers, "modifiers");
            return this;
        }

        public Builder step(ProgressStepperStep step) {
            steps.add(Objects.requireNonNull(step, "step"));
            return this;
        }

        public Builder steps(ProgressStepperStep... steps) {
            for (ProgressStepperStep step : steps) {
                step(step);
            }
            return this;
        }

        public ProgressStepper build() {
            if (steps.isEmpty()) {
                throw new IllegalStateException("A progress stepper needs at least one step");
            }
            return new ProgressStepper(this);
        }
    }
}
