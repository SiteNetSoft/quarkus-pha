import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-finite-step", 2)
        .range(0, 5).title("Finite step progress")
        .label("Step 2 of 5").valueText("Step 2 of 5").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
