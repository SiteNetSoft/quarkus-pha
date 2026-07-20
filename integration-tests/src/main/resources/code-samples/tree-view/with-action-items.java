import org.sitenetsoft.quarkus.pha.model.*;

TreeView tree = TreeView.builder()
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

// Template side, with `tree` in the template data:
// {#include components/navigation/tree-view tree=tree /}
