import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

Wizard wizard = Wizard.of("wiz-step-status")
        .step(Step.of("Succeeded step", "<h2 class=\"pf-v6-c-content--h2\">Information</h2><p>Information content.</p>").status("success"))
        .step(Step.of("Failed step", "<h2 class=\"pf-v6-c-content--h2\">Configuration</h2><p>Configuration content.</p>").status("danger"))
        .step(Step.of("Review", "<h2 class=\"pf-v6-c-content--h2\">Review</h2><p>Review content.</p>"))
        .footerStandard(true).build();

// Template side, with the data in scope:
// {#include components/navigation/wizard wizard=wizard /}
