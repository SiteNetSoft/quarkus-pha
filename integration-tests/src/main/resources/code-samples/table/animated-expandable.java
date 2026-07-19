import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-animated-expandable").ariaLabel("Animated expandable table example")
        .gridMd().modifier("pf-m-animate-expand")
        .toggleColumn()
        .column("Service").column("Status").column("Region")
        .row(Table.row("api-gateway", "Healthy", "us-east-1")
                .expandsTo("12 healthy instances across 3 availability zones. Average latency 42ms.")
                .expanded())
        .row(Table.row("payments", "Degraded", "eu-west-1")
                .expandsTo("8 healthy instances, 1 unhealthy. P99 latency 320ms (threshold 250ms)."))
        .row(Table.row("search", "Healthy", "ap-southeast-1")
                .expandsTo("Expandable row content has no padding.")
                .noPaddingDetail())
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
