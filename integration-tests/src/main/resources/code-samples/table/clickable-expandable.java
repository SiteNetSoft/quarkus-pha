import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-clickable-expandable").ariaLabel("Clickable and expandable table example")
        .gridMd()
        .toggleColumn()
        .clickable("staging")
        .column("Cluster").column("Nodes").column("Status")
        .row(Table.row("prod-us-east", "14", "Ready").key("prod")
                .expandsTo("Kubernetes 1.31, 14/14 nodes ready. Control plane spread across 3 zones."))
        .row(Table.row("staging", "6", "Ready").key("staging")
                .expandsTo("Mirrors prod topology at reduced scale. Auto-sleeps outside business hours."))
        .row(Table.row("dev-sandbox", "3", "Degraded").key("dev")
                .expandsTo("1 node cordoned for a kernel upgrade; workloads rescheduled automatically."))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
