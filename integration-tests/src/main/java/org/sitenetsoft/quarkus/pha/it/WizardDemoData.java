package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Wizard;
import org.sitenetsoft.quarkus.pha.model.Wizard.Step;

/**
 * Demo data for the wizard examples — the client-side Alpine wizards on
 * /components/wizard are populated from these models (basic, with-review and
 * with-substeps stay hand-written: server-driven HTMX wizards; within-modal, validate-button-press,
 * submit-progress and toggle-step-visibility stay hand-written: modal / live
 * icon-helper / submission / state-sharing-chrome compositions). Globals so the standalone example
 * route (which renders templates without data) can see them. Each is mirrored
 * by a snippet in resources/code-samples/wizard/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class WizardDemoData {

    private static String pane(String title, String body) {
        return "<h2 class=\"pf-v6-c-content--h2\">" + title + "</h2>\n<p>" + body + "</p>";
    }

    public static Wizard demoWizPlain = Wizard.of("wiz-plain").plain()
            .step(Step.of("Information", pane("Information", "Information content.")))
            .step(Step.of("Configuration", pane("Configuration", "Configuration content.")))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerStandard(true).build();

    public static Wizard demoWizCustomNavItem = Wizard.of("wiz-custom-nav-item")
            .step(Step.of("Information", pane("Information", "Information content.")).stepNum("1"))
            .step(Step.of("Configuration", pane("Configuration", "Configuration content.")).stepNum("2"))
            .step(Step.of("Review", pane("Review", "Review content.")).stepNum("3"))
            .footerStandard(true).build();

    public static Wizard demoWizCustomFooter = Wizard.of("wiz-custom-footer")
            .step(Step.of("Information", pane("Information", "Information content.")))
            .step(Step.of("Configuration", pane("Configuration", "Configuration content.")))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerButton("Continue", "pf-m-primary", null, "step === 3", "step = step + 1")
            .footerButton("Go back", "pf-m-secondary", null, "step === 1", "step = step - 1")
            .footerButton("Save draft", "pf-m-tertiary", null, null, null)
            .footerButton("Abandon", "pf-m-link pf-m-danger", null, null, null)
            .build();

    public static Wizard demoWizDisabledSteps = Wizard.of("wiz-disabled-steps")
            .step(Step.of("Information", pane("Information", "Information content.")))
            .step(Step.disabledStep("Locked step"))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerStandard(true).build();

    public static Wizard demoWizStepStatus = Wizard.of("wiz-step-status")
            .step(Step.of("Succeeded step", pane("Information", "Information content.")).status("success"))
            .step(Step.of("Failed step", pane("Configuration", "Configuration content.")).status("danger"))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerStandard(true).build();

    public static Wizard demoWizFormValidation = Wizard.of("wiz-form-validation")
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
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerButton("Next", "pf-m-primary", null, "step === 2 || name.trim() === ''", "step = 2")
            .footerButton("Back", "pf-m-secondary", null, "step === 1", "step = 1")
            .build();

    public static Wizard demoWizProgressive = Wizard.of("wiz-progressive-steps")
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
            .step(Step.of("Custom configuration", pane("Custom settings", "Custom settings content."))
                    .showExpr("custom"))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerButton("Next", "pf-m-primary", null, "step === 3",
                    "step = (step === 1 && mode === 'custom') ? 2 : 3")
            .footerButton("Back", "pf-m-secondary", null, "step === 1",
                    "step = (step === 3 && mode === 'custom') ? 2 : 1")
            .build();


    public static Wizard demoWizNavAnchors = Wizard.of("wiz-nav-anchors").anchors()
            .step(Step.of("Information", pane("Information", "Information content.")))
            .step(Step.of("Configuration", pane("Configuration", "Configuration content.")))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerStandard(true).build();

    public static Wizard demoWizHeader = Wizard.of("wiz-header")
            .header("Create cluster", "Provision a new cluster in three steps.")
            .step(Step.of("Information", pane("Information", "Information content.")))
            .step(Step.of("Configuration", pane("Configuration", "Configuration content.")))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerStandard(true).build();

    public static Wizard demoWizExpandable = Wizard.of("wiz-expandable-steps")
            .step(Step.of("Information", pane("Information", "Information content.")))
            .expandableGroup("Configuration",
                    Step.of("Network", pane("Network", "Network content.")),
                    Step.of("Storage", pane("Storage", "Storage content.")))
            .step(Step.of("Review", pane("Review", "Review content.")))
            .footerStandard(true).build();
}
