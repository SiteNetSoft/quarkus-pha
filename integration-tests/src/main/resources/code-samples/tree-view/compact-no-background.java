import org.sitenetsoft.quarkus.pha.model.*;

TreeView tree = TreeView.builder()
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

// Template side, with `tree` in the template data:
// {#include components/navigation/tree-view tree=tree /}
