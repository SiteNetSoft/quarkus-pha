import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-compact").ariaLabel("Progress steps")
        .compact()
        .step(ProgressStepperStep.of("First step").description("Completed").asSuccess())
        .step(ProgressStepperStep.of("Second step").description("In process").asCurrent()
                .spinner("Step in process"))
        .step(ProgressStepperStep.of("Third step").description("Pending").asPending())
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
