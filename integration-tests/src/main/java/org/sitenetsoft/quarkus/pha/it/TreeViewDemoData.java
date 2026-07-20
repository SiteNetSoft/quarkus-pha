package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.TreeView;
import org.sitenetsoft.quarkus.pha.model.TreeViewItem;

/**
 * Demo data for the tree-view examples — every example on
 * /components/tree-view is populated from one of these TreeView models.
 * Globals so the standalone example route (which renders templates without
 * data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/tree-view/ served on the example card's Java tab —
 * keep them in sync when editing.
 *
 * <p>Several childless nodes call {@code asExpandable()} — PatternFly's docs
 * examples show toggles on branches whose children simply aren't in the
 * markup, and the demos replicate that shape faithfully.
 */
@TemplateGlobal
public class TreeViewDemoData {

    public static TreeView demoTreeViewSingleSelectable = TreeView.builder()
            .ariaLabel("Tree View single selectable example")
            .toggleAllButton()
            .item(TreeViewItem.of("Application launcher").id("example1-AppLaunch").asExpanded()
                    .child(TreeViewItem.of("Application 1").id("example1-App1").asExpanded()
                            .child(TreeViewItem.of("Settings").id("example1-App1Settings"))
                            .child(TreeViewItem.of("Current").id("example1-App1Current")))
                    .child(TreeViewItem.of("Application 2").id("example1-App2").asExpanded()
                            .child(TreeViewItem.of("Settings").id("example1-App2Settings"))
                            .child(TreeViewItem.of("Loader").id("example1-App2Loader").asExpanded()
                                    .child(TreeViewItem.of("Loading App 1").id("example1-LoadApp1"))
                                    .child(TreeViewItem.of("Loading App 2").id("example1-LoadApp2"))
                                    .child(TreeViewItem.of("Loading App 3").id("example1-LoadApp3")))))
            .item(TreeViewItem.of("Cost management").id("example1-Cost").asExpanded()
                    .child(TreeViewItem.of("Application 3").id("example1-App3").asExpanded()
                            .child(TreeViewItem.of("Settings").id("example1-App3Settings"))
                            .child(TreeViewItem.of("Current").id("example1-App3Current"))))
            .item(TreeViewItem.of("Sources").id("example1-Sources").asExpanded()
                    .child(TreeViewItem.of("Application 4").id("example1-App4").asExpanded()
                            .child(TreeViewItem.of("Settings").id("example1-App4Settings"))))
            .item(TreeViewItem.of("Really really really long folder name that overflows the container it is in")
                    .id("example1-Long").asExpanded()
                    .child(TreeViewItem.of("Application 5").id("example1-App5")))
            .build();

    public static TreeView demoTreeViewMultiselectable = TreeView.builder()
            .ariaLabel("Tree View multiselectable example")
            .multiselectable()
            .item(TreeViewItem.of("Application launcher").asExpanded()
                    .child(TreeViewItem.of("Application 1").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current"))
                            .child(TreeViewItem.of("Loader").asExpandable()))
                    .child(TreeViewItem.of("Application 2").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Loader").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable())
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable())
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable())
            .build();

    public static TreeView demoTreeViewSeparateSelection = TreeView.builder()
            .ariaLabel("Tree View with selectable, expandable nodes example")
            .selectableNodes()
            .idPrefix("tree-view-separate-selection")
            .item(TreeViewItem.of("Application launcher").asExpanded()
                    .child(TreeViewItem.of("Application 1").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Options"))
                            .child(TreeViewItem.of("Loader").asExpandable()))
                    .child(TreeViewItem.of("Application 2").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Loader").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable())
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable())
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable())
            .build();

    public static TreeView demoTreeViewWithSearch = TreeView.builder()
            .ariaLabel("Tree View  with search example")
            .search("Search", "Search input")
            .item(TreeViewItem.of("Application launcher").asExpanded()
                    .child(TreeViewItem.of("Application 1").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current")))
                    .child(TreeViewItem.of("Application 2").asExpanded()
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Loader").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable())
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable())
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable())
            .build();

    public static TreeView demoTreeViewWithCheckboxes = TreeView.builder()
            .ariaLabel("Tree View with checkboxes example")
            .checkboxes()
            .idPrefix("tree-view-checkboxes")
            .item(TreeViewItem.of("Application launcher").asExpanded()
                    .child(TreeViewItem.of("Application 1").asExpanded().asChecked()
                            .child(TreeViewItem.of("Settings").asChecked())
                            .child(TreeViewItem.of("Loader").asChecked())
                            .child(TreeViewItem.of("Loader").asExpandable().asChecked()))
                    .child(TreeViewItem.of("Application 2").asExpanded()
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable().asChecked())
                                    .child(TreeViewItem.of("Loader app 2").asChecked())
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable())
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable().asChecked())
            .build();

    public static TreeView demoTreeViewWithIcons = TreeView.builder()
            .ariaLabel("Tree View with icons example")
            .defaultIcons("fa:folder", "fa:folder-open")
            .item(TreeViewItem.of("Application launcher").asExpanded()
                    .child(TreeViewItem.of("Application 1").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current")))
                    .child(TreeViewItem.of("Application 2").asExpanded()
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Loader").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable())
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable())
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable())
            .build();

    public static TreeView demoTreeViewUniqueIcons = TreeView.builder()
            .ariaLabel("Tree View with unique icon per item example")
            .item(TreeViewItem.of("Github accounts").asExpanded().icon("fa-brands:github")
                    .child(TreeViewItem.of("Account 1"))
                    .child(TreeViewItem.of("Account 2")))
            .item(TreeViewItem.of("Gitlab accounts").icon("fa-brands:gitlab")
                    .child(TreeViewItem.of("Account 3")))
            .item(TreeViewItem.of("Google accounts").icon("fa-brands:google")
                    .child(TreeViewItem.of("Account 4"))
                    .child(TreeViewItem.of("Account 5")))
            .build();

    public static TreeView demoTreeViewWithBadges = TreeView.builder()
            .ariaLabel("Tree View with badges example")
            .item(TreeViewItem.of("Application launcher").asExpanded().badge("2")
                    .child(TreeViewItem.of("Application 1").asExpanded().badge("2")
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current")))
                    .child(TreeViewItem.of("Application 2").asExpanded().badge("2")
                            .child(TreeViewItem.of("Settings").asExpandable().badge("2"))
                            .child(TreeViewItem.of("Loader").asExpanded().badge("2")
                                    .child(TreeViewItem.of("Loader app 1").asExpandable().badge("2"))
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable().badge("2"))
            .item(TreeViewItem.of("Sources").asExpandable().badge("2"))
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable().badge("2"))
            .build();

    public static TreeView demoTreeViewCustomBadges = TreeView.builder()
            .ariaLabel("Tree View with custom badges example")
            .item(TreeViewItem.of("Application launcher").asExpanded().badge("2 applications")
                    .child(TreeViewItem.of("Application 1").asExpanded().badge("2 children")
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current")))
                    .child(TreeViewItem.of("Application 2").asExpanded().badge("2 children")
                            .child(TreeViewItem.of("Settings").asExpandable().badge("3 loading apps"))
                            .child(TreeViewItem.of("Loader").asExpanded().badge("5 items")
                                    .child(TreeViewItem.of("Loader app 1").asExpandable().badge("2 children"))
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable().badge("3 settings"))
            .item(TreeViewItem.of("Sources").asExpandable().badge("4 items"))
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable().badge("2 nested"))
            .build();

    public static TreeView demoTreeViewActionItems = TreeView.builder()
            .ariaLabel("Tree View with action item example")
            .item(TreeViewItem.of("Application launcher").asExpanded().action("fa:ellipsis-v", "Actions")
                    .child(TreeViewItem.of("Application 1").asExpanded().action("fa:clipboard", "Copy")
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current")))
                    .child(TreeViewItem.of("Application 2").asExpanded().action("fa:bars", "Action")
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Loader").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable())
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable())
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable().action("fa:ellipsis-v", "Actions"))
            .build();

    public static TreeView demoTreeViewGuides = TreeView.builder()
            .ariaLabel("Tree View guides example")
            .guides()
            .item(TreeViewItem.of("Application launcher").asExpanded()
                    .child(TreeViewItem.of("Application 1").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current"))
                            .child(TreeViewItem.of("Loader").asExpandable()))
                    .child(TreeViewItem.of("Application 2").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Loader").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable())
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management").asExpandable())
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container.")
                    .asExpandable())
            .build();

    public static TreeView demoTreeViewCompact = TreeView.builder()
            .ariaLabel("Tree View compact example")
            .compact()
            .item(TreeViewItem.of("apiVersion").description(
                    "APIVersion defines the versioned schema of this representation of an object. Servers should "
                            + "convert recognized schemas to the latest internal value and may reject unrecognized values."))
            .item(TreeViewItem.of("kind").description(
                    "Kind is a string value representing the REST resource this object represents. Servers may infer "
                            + "this from the endpoint the client submits requests to. Cannot be updated is CamelCase. More info:"))
            .item(TreeViewItem.of("metadata").description("Standard object metadata"))
            .item(TreeViewItem.of("spec").asExpanded()
                    .description("Specification of the desired behavior of deployment.")
                    .child(TreeViewItem.of("minReadySeconds").description(
                            "Minimum number of seconds for which a newly created pod should be ready without any of "
                                    + "its container crashing, for it to be considered available. Default to 0 (pod will be "
                                    + "considered available as soon as it is ready)."))
                    .child(TreeViewItem.of("paused").description("Indicates that the deployment is paused"))
                    .child(TreeViewItem.of("progressDeadlineSeconds").description(
                            "The maximum time in seconds for a deployment to make progress before it is considered "
                                    + "to be failed. The deployment controller will continue to process failed deployments "
                                    + "and a condition with a ProgressDeadlineExceeded reason will be surfaced in the "
                                    + "deployment status. Note that the progress will not de estimated during the time a "
                                    + "deployment is paused. Defaults to 600s."))
                    .child(TreeViewItem.of("replicas").description(
                            "Number of desired pods. This is a pointer to distinguish between explicit zero and not "
                                    + "specified. Defaults to 1."))
                    .child(TreeViewItem.of("revisionHistoryLimit").description(
                            "The number of old ReplicaSets to retain to allow rollback. This is a pointer to "
                                    + "distinguish between explicit zero and not specified. Defaults to 10."))
                    .child(TreeViewItem.of("Selector").asExpanded()
                            .description("Label selector for pods. Existing ReplicaSets whose pods are selected by "
                                    + "this will be the ones affected by this deployment")
                            .child(TreeViewItem.of("matchExpressions").asExpanded()
                                    .description("matchExpressions is a list of the label selector requirements. "
                                            + "The requirements and ANDed.")
                                    .child(TreeViewItem.of("matchLabels").description(
                                            "matchExpressions is a list of the label selector requirements. "
                                                    + "The requirements and ANDed.")))
                            .child(TreeViewItem.of("matchLabels").description(
                                    "Map of {key.value} pairs. A single {key.value} in the matchLabels map is "
                                            + "equivalent to an element of matchExpressions, whose key field is \"key\", the "
                                            + "operator is \"In\" and the values array contains only \"value\". The "
                                            + "requirements are ANDed.")))
                    .child(TreeViewItem.of("matchLabels").description(
                            "Map of {key.value} pairs. A single {key.value} in the matchLabels map is equivalent to "
                                    + "an element of matchExpressions, whose key field is \"key\", the operator is \"In\" "
                                    + "and the values array contains only \"value\". The requirements are ANDed.")))
            .build();

    public static TreeView demoTreeViewCompactNoBackground = TreeView.builder()
            .ariaLabel("Tree View compact no background example")
            .compact()
            .noBackground()
            .item(TreeViewItem.of("apiVersion").description(
                    "APIVersion defines the versioned schema of this representation of an object. Servers should "
                            + "convert recognized schemas to the latest internal value and may reject unrecognized values."))
            .item(TreeViewItem.of("kind").description(
                    "Kind is a string value representing the REST resource this object represents. Servers may infer "
                            + "this from the endpoint the client submits requests to. Cannot be updated is CamelCase. More info:"))
            .item(TreeViewItem.of("metadata").description("Standard object metadata"))
            .item(TreeViewItem.of("spec").asExpanded()
                    .description("Specification of the desired behavior of deployment.")
                    .child(TreeViewItem.of("minReadySeconds").description(
                            "Minimum number of seconds for which a newly created pod should be ready without any of "
                                    + "its container crashing, for it to be considered available. Default to 0 (pod will be "
                                    + "considered available as soon as it is ready)."))
                    .child(TreeViewItem.of("paused").description("Indicates that the deployment is paused"))
                    .child(TreeViewItem.of("progressDeadlineSeconds").description(
                            "The maximum time in seconds for a deployment to make progress before it is considered "
                                    + "to be failed. The deployment controller will continue to process failed deployments "
                                    + "and a condition with a ProgressDeadlineExceeded reason will be surfaced in the "
                                    + "deployment status. Note that the progress will not de estimated during the time a "
                                    + "deployment is paused. Defaults to 600s."))
                    .child(TreeViewItem.of("replicas").description(
                            "Number of desired pods. This is a pointer to distinguish between explicit zero and not "
                                    + "specified. Defaults to 1."))
                    .child(TreeViewItem.of("revisionHistoryLimit").description(
                            "The number of old ReplicaSets to retain to allow rollback. This is a pointer to "
                                    + "distinguish between explicit zero and not specified. Defaults to 10."))
                    .child(TreeViewItem.of("Selector").asExpanded()
                            .description("Label selector for pods. Existing ReplicaSets whose pods are selected by "
                                    + "this will be the ones affected by this deployment")
                            .child(TreeViewItem.of("matchExpressions").asExpanded()
                                    .description("matchExpressions is a list of the label selector requirements. "
                                            + "The requirements and ANDed.")
                                    .child(TreeViewItem.of("matchLabels").description(
                                            "matchExpressions is a list of the label selector requirements. "
                                                    + "The requirements and ANDed.")))
                            .child(TreeViewItem.of("matchLabels").description(
                                    "Map of {key.value} pairs. A single {key.value} in the matchLabels map is "
                                            + "equivalent to an element of matchExpressions, whose key field is \"key\", the "
                                            + "operator is \"In\" and the values array contains only \"value\". The "
                                            + "requirements are ANDed.")))
                    .child(TreeViewItem.of("matchLabels").description(
                            "Map of {key.value} pairs. A single {key.value} in the matchLabels map is equivalent to "
                                    + "an element of matchExpressions, whose key field is \"key\", the operator is \"In\" "
                                    + "and the values array contains only \"value\". The requirements are ANDed.")))
            .build();

    public static TreeView demoTreeViewNonExpandableTopLevel = TreeView.builder()
            .ariaLabel("Tree View with non-expandable top level nodes example")
            .item(TreeViewItem.of("Application launcher").asExpanded()
                    .child(TreeViewItem.of("Application 1").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Current"))
                            .child(TreeViewItem.of("Loader").asExpandable()))
                    .child(TreeViewItem.of("Application 2").asExpanded()
                            .child(TreeViewItem.of("Settings"))
                            .child(TreeViewItem.of("Settings").asExpandable())
                            .child(TreeViewItem.of("Loader").asExpanded()
                                    .child(TreeViewItem.of("Loader app 1").asExpandable())
                                    .child(TreeViewItem.of("Loader app 2"))
                                    .child(TreeViewItem.of("Loader app 3")))))
            .item(TreeViewItem.of("Cost management"))
            .item(TreeViewItem.of("Sources").asExpandable())
            .item(TreeViewItem.of(
                    "This is a really really really long folder name that overflows from the width of the container."))
            .build();
}
