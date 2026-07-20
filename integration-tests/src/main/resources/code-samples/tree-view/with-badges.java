import org.sitenetsoft.quarkus.pha.model.*;

TreeView tree = TreeView.builder()
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

// Template side, with `tree` in the template data:
// {#include components/navigation/tree-view tree=tree /}
