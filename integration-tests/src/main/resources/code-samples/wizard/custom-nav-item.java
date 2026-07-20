import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

Wizard wizard = Wizard.of("wiz-custom-nav-item")
        .step(Step.of("Information", "<h2 class=\"pf-v6-c-content--h2\">Information</h2><p>Information content.</p>").stepNum("1"))
        .step(Step.of("Configuration", "<h2 class=\"pf-v6-c-content--h2\">Configuration</h2><p>Configuration content.</p>").stepNum("2"))
        .step(Step.of("Review", "<h2 class=\"pf-v6-c-content--h2\">Review</h2><p>Review content.</p>").stepNum("3"))
        .footerStandard(true).build();

// Template side, with the data in scope:
// {#include components/navigation/wizard wizard=wizard /}
