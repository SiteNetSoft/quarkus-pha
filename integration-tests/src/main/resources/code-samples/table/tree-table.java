import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-tree").ariaLabel("Tree table of servers")
        .column("Name").column("Type").column("Detail")
        .treeNode(Table.node("Application servers", "Group", "2 nodes").expanded()
                .child(Table.node("web-1", "Server", "2 vCPU · us-east-1"))
                .child(Table.node("web-2", "Server", "2 vCPU · us-east-1")))
        .treeNode(Table.node("Databases", "Group", "1 node")
                .child(Table.node("db-primary", "Server", "8 vCPU · eu-west-1")))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
