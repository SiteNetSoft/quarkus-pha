import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

Wizard wizard = Wizard.of("wiz-expandable-steps")
        .step(Step.of("Information", "<h2 class=\"pf-v6-c-content--h2\">Information</h2><p>Information content.</p>"))
        .expandableGroup("Configuration",
                Step.of("Network", "<h2 class=\"pf-v6-c-content--h2\">Network</h2><p>Network content.</p>"),
                Step.of("Storage", "<h2 class=\"pf-v6-c-content--h2\">Storage</h2><p>Storage content.</p>"))
        .step(Step.of("Review", "<h2 class=\"pf-v6-c-content--h2\">Review</h2><p>Review content.</p>"))
        .footerStandard(true).build();

// Template side, with the data in scope:
// {#include components/navigation/wizard wizard=wizard /}
