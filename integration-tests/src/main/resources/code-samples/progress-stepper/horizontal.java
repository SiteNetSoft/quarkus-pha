import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-horizontal").ariaLabel("Progress steps")
        .step(ProgressStepperStep.of("Step 1").description("Completed").asSuccess())
        .step(ProgressStepperStep.of("Step 2").description("In progress").asCurrent())
        .step(ProgressStepperStep.of("Step 3").description("Pending"))
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
