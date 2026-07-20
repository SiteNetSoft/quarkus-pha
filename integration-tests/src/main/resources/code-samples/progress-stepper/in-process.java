import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-in-process").ariaLabel("Progress steps")
        .step(ProgressStepperStep.of("Successful completion").asSuccess())
        .step(ProgressStepperStep.of("In process").asCurrent().spinner("Step in process"))
        .step(ProgressStepperStep.of("Pending"))
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
