import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-compact-expandable").ariaLabel("Compact expandable table example")
        .compact().gridMd()
        .toggleColumn()
        .column("Pipeline").column("Last run").column("Duration")
        .row(Table.row("build-frontend", "Passed", "4m 12s")
                .expandsTo("312 tests, 0 failures. Artifacts published to the snapshot repository."))
        .row(Table.row("build-backend", "Failed", "7m 45s")
                .expandsTo("2 integration tests failed in the persistence module; see run #482 for logs."))
        .row(Table.row("nightly-e2e", "Passed", "28m 03s")
                .expandsTo("Full browser matrix green. Flake rate 0.4% over the last 30 runs."))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
