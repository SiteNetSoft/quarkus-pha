import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

Wizard wizard = Wizard.of("wiz-custom-footer")
        .step(Step.of("Information", "<h2 class=\"pf-v6-c-content--h2\">Information</h2><p>Information content.</p>"))
        .step(Step.of("Configuration", "<h2 class=\"pf-v6-c-content--h2\">Configuration</h2><p>Configuration content.</p>"))
        .step(Step.of("Review", "<h2 class=\"pf-v6-c-content--h2\">Review</h2><p>Review content.</p>"))
        .footerButton("Continue", "primary", null, "step === 3", "step = step + 1")
        .footerButton("Go back", "secondary", null, "step === 1", "step = step - 1")
        .footerButton("Save draft", "tertiary", null, null, null)
        .footerButton(Button.of("Abandon").variant("link").asDanger().build(), null, null, null)
        .build();

// Template side, with the data in scope:
// {#include components/navigation/wizard wizard=wizard /}
