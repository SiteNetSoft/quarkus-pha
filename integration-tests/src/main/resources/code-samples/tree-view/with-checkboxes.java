import org.sitenetsoft.quarkus.pha.model.*;

TreeView tree = TreeView.builder()
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

// Template side, with `tree` in the template data:
// {#include components/navigation/tree-view tree=tree /}
