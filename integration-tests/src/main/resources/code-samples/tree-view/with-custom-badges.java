import org.sitenetsoft.quarkus.pha.model.*;

TreeView tree = TreeView.builder()
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

// Template side, with `tree` in the template data:
// {#include components/navigation/tree-view tree=tree /}
