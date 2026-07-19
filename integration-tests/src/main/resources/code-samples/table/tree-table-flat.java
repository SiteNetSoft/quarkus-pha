import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-tree-flat").ariaLabel("Flat tree table with no indentation")
        .treeGridLg().treeNoInset()
        .column("Name").column("Type").column("Detail")
        .treeNode(Table.node("web-1", "Server", "2 vCPU · us-east-1"))
        .treeNode(Table.node("web-2", "Server", "2 vCPU · us-east-1"))
        .treeNode(Table.node("db-primary", "Server", "8 vCPU · eu-west-1"))
        .treeNode(Table.node("cache-1", "Server", "4 vCPU · us-east-1"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
