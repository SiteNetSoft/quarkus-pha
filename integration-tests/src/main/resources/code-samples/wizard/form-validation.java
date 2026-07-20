import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

Wizard wizard = Wizard.of("wiz-form-validation")
        .stateVars("name: ''")
        .step(Step.of("Name", """
                <h2 class="pf-v6-c-content--h2">Name</h2>
                <form class="pf-v6-c-form" @submit.prevent>
                  <div class="pf-v6-c-form__group">
                    <div class="pf-v6-c-form__group-label">
                      <label class="pf-v6-c-form__label" for="wiz-form-validation-name">
                        <span class="pf-v6-c-form__label-text">Cluster name</span>
                        <span class="pf-v6-c-form__label-required" aria-hidden="true">&#42;</span>
                      </label>
                    </div>
                    <div class="pf-v6-c-form__group-control">
                      <span class="pf-v6-c-form-control pf-m-required"><input type="text" id="wiz-form-validation-name" x-model="name" /></span>
                    </div>
                  </div>
                </form>"""))
        .step(Step.of("Review", "<h2 class=\"pf-v6-c-content--h2\">Review</h2><p>Review content.</p>"))
        .footerButton("Next", "pf-m-primary", null, "step === 2 || name.trim() === ''", "step = 2")
        .footerButton("Back", "pf-m-secondary", null, "step === 1", "step = 1")
        .build();

// Template side, with the data in scope:
// {#include components/navigation/wizard wizard=wizard /}
