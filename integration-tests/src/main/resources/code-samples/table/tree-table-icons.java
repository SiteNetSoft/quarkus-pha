import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-tree-icons").ariaLabel("Tree table with checkboxes and icons")
        .treeCheckboxes().treeGridLg()
        .column("Name").column("Type").column("Detail")
        .treeNode(Table.node("Application servers", "Group", "2 nodes").expanded()
                .icon("fa-folder", "fa-folder-open")
                .child(Table.node("web-1", "Server", "2 vCPU · us-east-1").key("web1").checked().icon("fa-leaf"))
                .child(Table.node("web-2", "Server", "2 vCPU · us-east-1").key("web2").icon("fa-leaf")))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
