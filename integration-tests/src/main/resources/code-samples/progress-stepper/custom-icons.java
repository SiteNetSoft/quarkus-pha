import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-custom-icons").ariaLabel("Progress steps")
        .step(ProgressStepperStep.of("Code").description("Pushed to main").asCustom().icon("fa:code"))
        .step(ProgressStepperStep.of("Build").description("Image built").asCustom().icon("fa:cube").asCurrent())
        .step(ProgressStepperStep.of("Ship").description("Awaiting approval").asPending().icon("fa:rocket"))
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
