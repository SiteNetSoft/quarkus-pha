import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-compact-center").ariaLabel("Progress steps")
        .compact().center()
        .step(ProgressStepperStep.of("First step").asSuccess())
        .step(ProgressStepperStep.of("Second step").asCurrent())
        .step(ProgressStepperStep.of("Third step"))
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
