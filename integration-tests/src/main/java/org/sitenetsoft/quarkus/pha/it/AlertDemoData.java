package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Alert;

import java.util.List;

/**
 * Demo data for the alert examples — the static examples on /components/alert
 * are populated from these Alert models (the timeout, dynamic-group and toast
 * demos stay hand-written: their list-mutating Alpine state is a composition
 * outside the model's scope). Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/alert/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class AlertDemoData {

    public static List<Alert> demoAlertVariants = List.of(
            Alert.of("Info alert").id("al-info").variant("info").build(),
            Alert.of("Success alert").id("al-success").variant("success").build(),
            Alert.of("Warning alert").id("al-warning").variant("warning").build(),
            Alert.of("Danger alert").id("al-danger").variant("danger").build(),
            Alert.of("Custom alert").id("al-custom").variant("custom").build());

    public static List<Alert> demoAlertVariations = List.of(
            Alert.of("Success alert with everything").id("al-variation-full").variant("success")
                    .description("A description paragraph adds detail below the title.")
                    .asClosable()
                    .actionLink("View details").actionLink("Ignore")
                    .build(),
            Alert.of("Success alert with description").id("al-variation-desc").variant("success")
                    .description("Title plus description only.").build(),
            Alert.of("Success alert title only").id("al-variation-plain-title").variant("success").build());

    public static Alert demoAlertInline = Alert.of("Inline alert").id("al-inline").variant("warning")
            .asInline()
            .description("Inline variant — sits inside content blocks without the boxed treatment.")
            .build();

    public static List<Alert> demoAlertsPlainInline = List.of(
            Alert.of("Plain inline info alert").id("al-plain-info").variant("info").asInline().asPlain().build(),
            Alert.of("Plain inline success alert").id("al-plain-success").variant("success").asInline().asPlain().build(),
            Alert.of("Plain inline danger alert").id("al-plain-danger").variant("danger").asInline().asPlain().build());

    public static Alert demoAlertDescription = Alert.of("With description").id("al-desc").variant("info")
            .description("A second line of explanatory text under the title. Use this to add detail beyond the headline.")
            .build();

    public static Alert demoAlertActions = Alert.of("Deployment complete").variant("success")
            .description("Your application is live. Review the deployment or roll back if something looks wrong.")
            .actionLink("View deployment", "#").actionLink("Roll back", "#")
            .build();

    public static Alert demoAlertClosable = Alert.of("Dismissable alert").id("al-close").variant("info")
            .asClosable().build();

    public static List<Alert> demoAlertsCustomIcons = List.of(
            Alert.of("Success alert with a custom rocket icon").id("al-custom-icon-1").variant("success")
                    .customIcon("fa:rocket").build(),
            Alert.of("Warning alert with a custom clock icon").id("al-custom-icon-2").variant("warning")
                    .customIcon("fa:clock").build());

    public static Alert demoAlertExpandable = Alert.of("Expandable info alert").id("al-expandable").variant("info")
            .description("The description and action links stay hidden until the alert is expanded.")
            .asExpandable()
            .actionLink("View details")
            .build();

    public static Alert demoAlertTruncated = Alert
            .of("Really long info alert title that will be truncated to a single line by the pf-m-truncate title modifier no matter how much text keeps going and going and going")
            .id("al-truncated").variant("info").asTruncated().build();

    public static Alert demoAlertLiveStatic = Alert.of("Static live region alert").id("al-live-static").variant("info")
            .asInline()
            .description("Screen readers announce updates to this region without moving focus.")
            .build();

    public static List<Alert> demoAlertGroupStatic = List.of(
            Alert.of("Success alert").id("al-group-static-1").variant("success").asInline().build(),
            Alert.of("Info alert").id("al-group-static-2").variant("info").asInline().build(),
            Alert.of("Warning alert").id("al-group-static-3").variant("warning").asInline().build());
}
