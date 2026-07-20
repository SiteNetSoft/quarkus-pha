import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

Wizard wizard = Wizard.of("wiz-disabled-steps")
        .step(Step.of("Information", "<h2 class=\"pf-v6-c-content--h2\">Information</h2><p>Information content.</p>"))
        .step(Step.disabledStep("Locked step"))
        .step(Step.of("Review", "<h2 class=\"pf-v6-c-content--h2\">Review</h2><p>Review content.</p>"))
        .footerStandard(true).build();

// Template side, with the data in scope:
// {#include components/navigation/wizard wizard=wizard /}
