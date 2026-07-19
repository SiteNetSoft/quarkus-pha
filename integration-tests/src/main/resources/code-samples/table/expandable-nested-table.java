import org.sitenetsoft.quarkus.pha.model.*;

Table currentRollout = Table.builder()
        .ariaLabel("Rollout steps for release-2.15").compact().borderless().dataLabels()
        .column("Step").column("Finished").column("Result")
        .row("Database migration", "14:02", "Succeeded")
        .row("Canary (5% traffic)", "14:20", "Succeeded")
        .row("Full rollout", "—", "Running")
        .build();

Table previousRollout = Table.builder()
        .ariaLabel("Rollout steps for release-2.14").compact().borderless().dataLabels()
        .column("Step").column("Finished").column("Result")
        .row("Database migration", "May 28, 10:14", "Succeeded")
        .row("Canary (5% traffic)", "May 28, 10:31", "Succeeded")
        .row("Full rollout", "May 28, 11:05", "Succeeded")
        .build();

Table table = Table.builder()
        .id("tbl-expandable-nested").ariaLabel("Expandable table with nested table")
        .gridMd()
        .toggleColumn()
        .column("Deployment").column("Environment").column("Status")
        .row(Table.row("release-2.15", "Production", "In progress")
                .expandsToTable(currentRollout)
                .expanded()
                .detailAriaLabel("Toggle rollout steps for release-2.15"))
        .row(Table.row("release-2.14", "Production", "Complete")
                .expandsToTable(previousRollout)
                .detailAriaLabel("Toggle rollout steps for release-2.14"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
