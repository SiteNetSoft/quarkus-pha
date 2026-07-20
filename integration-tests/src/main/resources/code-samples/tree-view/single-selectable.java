import org.sitenetsoft.quarkus.pha.model.*;

TreeView tree = TreeView.builder()
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

// Template side, with `tree` in the template data:
// {#include components/navigation/tree-view tree=tree /}
