import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-animated-compound").ariaLabel("Animated compound expandable table example")
        .gridMd().modifier("pf-m-animate-expand")
        .column("Workspace").column("Members").column("Projects").column("Last activity")
        .row(TableCell.text("siemur/test-space").asRowHeader(),
                TableCell.compound("5 members", "members",
                        "Amelia, Jordan, Priya, Sam and Tomás have access; Amelia is the owner.").expanded(),
                TableCell.compound("3 projects", "projects",
                        "Projects: storefront, billing-service and design-tokens."),
                TableCell.text("20 minutes ago"))
        .row(TableCell.text("siemur/staging").asRowHeader(),
                TableCell.compound("2 members", "members",
                        "Jordan and Priya have access; Jordan is the owner."),
                TableCell.compound("1 project", "projects", "Projects: load-test-harness."),
                TableCell.text("2 days ago"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
