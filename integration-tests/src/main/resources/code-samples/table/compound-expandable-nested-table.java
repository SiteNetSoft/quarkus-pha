import org.sitenetsoft.quarkus.pha.model.*;

Table runtimeBranches = Table.builder()
        .ariaLabel("Branches of pha-runtime").compact().borderless().dataLabels()
        .column("Branch").column("Last commit").column("Status")
        .row("main", "2 hours ago", "Passing")
        .row("develop", "20 minutes ago", "Passing")
        .row("feature/icons", "3 days ago", "Failing")
        .build();

Table deploymentBranches = Table.builder()
        .ariaLabel("Branches of pha-deployment").compact().borderless().dataLabels()
        .column("Branch").column("Last commit").column("Status")
        .row("main", "1 day ago", "Passing")
        .row("develop", "4 hours ago", "Passing")
        .build();

Table table = Table.builder()
        .id("tbl-compound-nested").ariaLabel("Compound expandable table with nested table")
        .gridMd()
        .column("Repository").column("Branches").column("Pull requests").column("Workspaces")
        .row(TableCell.text("pha-runtime").asRowHeader(),
                TableCell.compoundTable("4 branches", "branches", runtimeBranches).expanded(),
                TableCell.compound("2 pull requests", "prs", "2 pull requests currently under review.")
                        .noPaddingDetail().noBackgroundDetail(),
                TableCell.text("3 workspaces"))
        .row(TableCell.text("pha-deployment").asRowHeader(),
                TableCell.compoundTable("2 branches", "branches", deploymentBranches),
                TableCell.compound("1 pull request", "prs", "1 pull request currently under review.")
                        .noPaddingDetail().noBackgroundDetail(),
                TableCell.text("1 workspace"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
