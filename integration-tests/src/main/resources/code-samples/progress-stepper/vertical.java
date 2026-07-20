import org.sitenetsoft.quarkus.pha.model.*;

ProgressStepper stepper = ProgressStepper.builder()
        .id("ps-vertical").ariaLabel("Progress steps")
        .vertical()
        .step(ProgressStepperStep.of("Configure").description("Pipeline configured.").asSuccess())
        .step(ProgressStepperStep.of("Build").description("Compiling sources…").asCurrent())
        .step(ProgressStepperStep.of("Deploy").description("Waiting for build.").asPending())
        .build();

// Template side, with `stepper` in the template data:
// {#include components/feedback/progress-stepper stepper=stepper /}
