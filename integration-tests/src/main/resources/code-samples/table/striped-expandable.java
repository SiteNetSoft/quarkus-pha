import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-striped-expandable").ariaLabel("Striped expandable table example")
        .striped().gridMd()
        .toggleColumn()
        .column("Backup job").column("Schedule").column("Last result")
        .row(Table.row("postgres-daily", "02:00 UTC", "Succeeded")
                .expandsTo("42 GB snapshot in 11 minutes; verified restore on the standby cluster."))
        .row(Table.row("object-store-weekly", "Sun 04:00 UTC", "Succeeded")
                .expandsTo("1.8 TB incremental to cold storage; 97% deduplication ratio."))
        .row(Table.row("config-hourly", "Hourly", "Failed")
                .expandsTo("Vault token expired at 13:00; renewal issued and the 14:00 run recovered."))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
