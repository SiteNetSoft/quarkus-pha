import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-expandable").ariaLabel("Expandable table example")
        .toggleColumn()
        .column("Name").column("Status").column("Region")
        .row(Table.row("api-gateway", "Healthy", "us-east-1")
                .expandsTo("12 healthy instances across 3 availability zones. Average latency 42ms. Last deployed 4 hours ago.")
                .paragraphDetail().detailAriaLabel("Toggle row details for api-gateway"))
        .row(Table.row("payments", "Degraded", "eu-west-1")
                .expandsTo("8 healthy instances, 1 unhealthy. P99 latency 320ms (threshold 250ms). Oncall has been paged.")
                .paragraphDetail().detailAriaLabel("Toggle row details for payments"))
        .row(Table.row("search", "Healthy", "ap-southeast-1")
                .expandsTo("4 healthy instances. Index size 2.4 GB across 16 shards. No alerts.")
                .paragraphDetail().detailAriaLabel("Toggle row details for search"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
