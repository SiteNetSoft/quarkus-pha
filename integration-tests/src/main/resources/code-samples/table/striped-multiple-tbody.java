import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-striped-tbody").ariaLabel("Striped table with multiple tbody elements").gridMd()
        .caption("Production and staging deployments")
        .column("Service").column("Version").column("Replicas").column("Status")
        .body(TableBody.Stripe.ODD)
        .row("api-gateway (prod)", "2.14.0", "6", "Running")
        .row("payments (prod)", "1.9.2", "4", "Running")
        .row("search (prod)", "3.1.0", "3", "Running")
        .body(TableBody.Stripe.EVEN)
        .row("api-gateway (staging)", "2.15.0-rc.1", "2", "Running")
        .row("payments (staging)", "1.10.0-rc.2", "1", "Crash-looping")
        .row("search (staging)", "3.2.0-rc.1", "1", "Running")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
