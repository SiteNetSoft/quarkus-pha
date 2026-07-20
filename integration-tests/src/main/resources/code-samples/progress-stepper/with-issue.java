import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-issue").ariaLabel("Progress steps")
        .step(ProgressStepperStep.of("Built").asSuccess())
        .step(ProgressStepperStep.of("Tests passed with warnings").asWarning())
        .step(ProgressStepperStep.of("Deploying").asCurrent())
        .step(ProgressStepperStep.of("Released"))
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
