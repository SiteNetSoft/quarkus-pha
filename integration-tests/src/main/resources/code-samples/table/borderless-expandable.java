import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-borderless-expandable").ariaLabel("Borderless expandable table example")
        .borderless().gridMd()
        .toggleColumn()
        .column("Dataset").column("Rows").column("Updated")
        .row(Table.row("orders-2026", "1.2M", "Hourly")
                .expandsTo("Partitioned by order date; retained for 24 months. Sourced from the checkout event stream."))
        .row(Table.row("customers", "840K", "Daily")
                .expandsTo("Deduplicated nightly against the CRM export; PII columns are masked outside production."))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
