import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-vertical-responsive").ariaLabel("Progress steps")
        .modifiers("pf-m-vertical-on-lg pf-m-horizontal-on-2xl")
        .step(ProgressStepperStep.of("First step").description("Completed").asSuccess())
        .step(ProgressStepperStep.of("Second step").description("In progress").asCurrent())
        .step(ProgressStepperStep.of("Third step").description("Pending"))
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
