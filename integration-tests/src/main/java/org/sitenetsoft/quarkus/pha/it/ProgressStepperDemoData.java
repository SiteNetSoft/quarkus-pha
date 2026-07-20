package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.ProgressStepper;
import org.sitenetsoft.quarkus.pha.model.ProgressStepperStep;

/**
 * Demo data for the progress-stepper examples — every model-driven example on
 * /components/progress-stepper is populated from one of these ProgressStepper
 * models (help-popover stays hand-written: its title doubles as a live Alpine
 * popover trigger, a composition outside the model's scope). Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in
 * resources/code-samples/progress-stepper/ served on the example card's Java
 * tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ProgressStepperDemoData {

    public static ProgressStepper demoStepperHorizontal = ProgressStepper.builder()
            .id("ps-horizontal").ariaLabel("Progress steps")
            .step(ProgressStepperStep.of("Step 1").description("Completed").asSuccess())
            .step(ProgressStepperStep.of("Step 2").description("In progress").asCurrent())
            .step(ProgressStepperStep.of("Step 3").description("Pending"))
            .build();

    public static ProgressStepper demoStepperVertical = ProgressStepper.builder()
            .id("ps-vertical").ariaLabel("Progress steps")
            .vertical()
            .step(ProgressStepperStep.of("Configure").description("Pipeline configured.").asSuccess())
            .step(ProgressStepperStep.of("Build").description("Compiling sources…").asCurrent())
            .step(ProgressStepperStep.of("Deploy").description("Waiting for build.").asPending())
            .build();

    public static ProgressStepper demoStepperCompact = ProgressStepper.builder()
            .id("ps-compact").ariaLabel("Progress steps")
            .compact()
            .step(ProgressStepperStep.of("First step").description("Completed").asSuccess())
            .step(ProgressStepperStep.of("Second step").description("In process").asCurrent()
                    .spinner("Step in process"))
            .step(ProgressStepperStep.of("Third step").description("Pending").asPending())
            .build();

    public static ProgressStepper demoStepperWithAlignment = ProgressStepper.builder()
            .id("ps-alignment").ariaLabel("Progress steps")
            .center()
            .step(ProgressStepperStep.of("First step").description("Completed").asSuccess())
            .step(ProgressStepperStep.of("Second step").description("In progress").asCurrent())
            .step(ProgressStepperStep.of("Third step").description("Pending"))
            .build();

    public static ProgressStepper demoStepperWithIssue = ProgressStepper.builder()
            .id("ps-issue").ariaLabel("Progress steps")
            .step(ProgressStepperStep.of("Built").asSuccess())
            .step(ProgressStepperStep.of("Tests passed with warnings").asWarning())
            .step(ProgressStepperStep.of("Deploying").asCurrent())
            .step(ProgressStepperStep.of("Released"))
            .build();

    public static ProgressStepper demoStepperWithFailure = ProgressStepper.builder()
            .id("ps-failure").ariaLabel("Progress steps")
            .step(ProgressStepperStep.of("Built").asSuccess())
            .step(ProgressStepperStep.of("Tests").asSuccess())
            .step(ProgressStepperStep.of("Deploy failed").asDanger().asCurrent())
            .step(ProgressStepperStep.of("Released"))
            .build();

    public static ProgressStepper demoStepperCustomIcons = ProgressStepper.builder()
            .id("ps-custom-icons").ariaLabel("Progress steps")
            .step(ProgressStepperStep.of("Code").description("Pushed to main").asCustom().icon("fa:code"))
            .step(ProgressStepperStep.of("Build").description("Image built").asCustom().icon("fa:cube").asCurrent())
            .step(ProgressStepperStep.of("Ship").description("Awaiting approval").asPending().icon("fa:rocket"))
            .build();

    public static ProgressStepper demoStepperInProcess = ProgressStepper.builder()
            .id("ps-in-process").ariaLabel("Progress steps")
            .step(ProgressStepperStep.of("Successful completion").asSuccess())
            .step(ProgressStepperStep.of("In process").asCurrent().spinner("Step in process"))
            .step(ProgressStepperStep.of("Pending"))
            .build();

    public static ProgressStepper demoStepperVerticalResponsive = ProgressStepper.builder()
            .id("ps-vertical-responsive").ariaLabel("Progress steps")
            .modifiers("pf-m-vertical-on-lg pf-m-horizontal-on-2xl")
            .step(ProgressStepperStep.of("First step").description("Completed").asSuccess())
            .step(ProgressStepperStep.of("Second step").description("In progress").asCurrent())
            .step(ProgressStepperStep.of("Third step").description("Pending"))
            .build();

    public static ProgressStepper demoStepperCenterVertical = ProgressStepper.builder()
            .id("ps-center-vertical").ariaLabel("Progress steps")
            .center().vertical()
            .step(ProgressStepperStep.of("First step").description("Completed").asSuccess())
            .step(ProgressStepperStep.of("Second step").description("In progress").asCurrent())
            .step(ProgressStepperStep.of("Third step").description("Pending"))
            .build();

    public static ProgressStepper demoStepperCompactVertical = ProgressStepper.builder()
            .id("ps-compact-vertical").ariaLabel("Progress steps")
            .compact().vertical()
            .step(ProgressStepperStep.of("First step").asSuccess())
            .step(ProgressStepperStep.of("Second step").asCurrent())
            .step(ProgressStepperStep.of("Third step"))
            .build();

    public static ProgressStepper demoStepperCompactVerticalResponsive = ProgressStepper.builder()
            .id("ps-compact-vertical-responsive").ariaLabel("Progress steps")
            .compact()
            .modifiers("pf-m-vertical-on-lg pf-m-horizontal-on-xl")
            .step(ProgressStepperStep.of("First step").asSuccess())
            .step(ProgressStepperStep.of("Second step").asCurrent())
            .step(ProgressStepperStep.of("Third step"))
            .build();

    public static ProgressStepper demoStepperCompactVerticalCenter = ProgressStepper.builder()
            .id("ps-compact-vertical-center").ariaLabel("Progress steps")
            .compact().vertical().center()
            .step(ProgressStepperStep.of("First step").asSuccess())
            .step(ProgressStepperStep.of("Second step").asCurrent())
            .step(ProgressStepperStep.of("Third step"))
            .build();

    public static ProgressStepper demoStepperCompactCenter = ProgressStepper.builder()
            .id("ps-compact-center").ariaLabel("Progress steps")
            .compact().center()
            .step(ProgressStepperStep.of("First step").asSuccess())
            .step(ProgressStepperStep.of("Second step").asCurrent())
            .step(ProgressStepperStep.of("Third step"))
            .build();
}
