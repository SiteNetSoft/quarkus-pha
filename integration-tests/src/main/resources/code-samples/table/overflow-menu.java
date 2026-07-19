import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-overflow-menu").ariaLabel("Table with overflow menus").dataLabels()
        .column("Job").column("Queue").column("State").actionColumn()
        .row("nightly-report", "batch", "Idle",
                TableCell.overflowMenu("nightly-report actions",
                        TableAction.primary("Start"), TableAction.secondary("Stop")))
        .row("index-rebuild", "maintenance", "Running",
                TableCell.overflowMenu("index-rebuild actions",
                        TableAction.primary("Start"), TableAction.secondary("Stop")))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
