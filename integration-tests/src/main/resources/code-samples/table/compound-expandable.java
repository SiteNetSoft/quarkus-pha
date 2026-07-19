import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-compound").ariaLabel("Compound expandable table")
        .column("Repository").column("Branches").column("Pull requests").column("Workspaces")
        .row("node-1",
                TableCell.compound("10 branches", "branches", "10 branches across 3 teams; default is main."),
                TableCell.compound("5 pull requests", "prs", "5 open pull requests, 2 awaiting review."),
                TableCell.compound("3 workspaces", "ws", "3 active workspaces, 1 archived."))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
