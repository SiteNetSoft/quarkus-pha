import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-tree-checkboxes").ariaLabel("Tree table with checkboxes")
        .treeCheckboxes().treeGridLg()
        .column("Name").column("Type").column("Detail")
        .treeNode(Table.node("Application servers", "Group", "2 nodes").expanded()
                .child(Table.node("web-1", "Server", "2 vCPU · us-east-1").key("web1"))
                .child(Table.node("web-2", "Server", "2 vCPU · us-east-1").key("web2")))
        .treeNode(Table.node("Databases", "Group", "1 node")
                .child(Table.node("db-primary", "Server", "8 vCPU · eu-west-1").key("db1")))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
