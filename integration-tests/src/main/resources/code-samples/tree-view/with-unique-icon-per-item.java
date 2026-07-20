import org.sitenetsoft.quarkus.pha.model.*;

TreeView tree = TreeView.builder()
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

// Template side, with `tree` in the template data:
// {#include components/navigation/tree-view tree=tree /}
