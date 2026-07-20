import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-step-instructions", 2)
        .range(0, 5).title("Onboarding")
        .label("Step 2 of 5").valueText("Step 2 of 5: configure billing")
        .helperText("Configure billing details. Next step: invite team members.").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
