import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

Wizard wizard = Wizard.of("wiz-progressive-steps")
        .stateVars("mode: 'quick', custom: false")
        .step(Step.of("Setup mode", """
                <h2 class="pf-v6-c-content--h2">Setup mode</h2>
                <div class="pf-v6-c-radio">
                  <input class="pf-v6-c-radio__input" type="radio" id="wiz-prog-quick" name="wiz-prog-mode" value="quick" x-model="mode" @change="custom = false" />
                  <label class="pf-v6-c-radio__label" for="wiz-prog-quick">Quick setup</label>
                </div>
                <div class="pf-v6-c-radio">
                  <input class="pf-v6-c-radio__input" type="radio" id="wiz-prog-custom" name="wiz-prog-mode" value="custom" x-model="mode" @change="custom = true" />
                  <label class="pf-v6-c-radio__label" for="wiz-prog-custom">Custom setup (adds a step)</label>
                </div>"""))
        .step(Step.of("Custom configuration", "<h2 class=\"pf-v6-c-content--h2\">Custom settings</h2><p>Custom settings content.</p>")
                .showExpr("custom"))
        .step(Step.of("Review", "<h2 class=\"pf-v6-c-content--h2\">Review</h2><p>Review content.</p>"))
        .footerButton("Next", "pf-m-primary", null, "step === 3",
                "step = (step === 1 && mode === 'custom') ? 2 : 3")
        .footerButton("Back", "pf-m-secondary", null, "step === 1",
                "step = (step === 3 && mode === 'custom') ? 2 : 1")
        .build();

// Template side, with the data in scope:
// {#include components/navigation/wizard wizard=wizard /}
