import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-failure").ariaLabel("Progress steps")
        .step(ProgressStepperStep.of("Built").asSuccess())
        .step(ProgressStepperStep.of("Tests").asSuccess())
        .step(ProgressStepperStep.of("Deploy failed").asDanger().asCurrent())
        .step(ProgressStepperStep.of("Released"))
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
