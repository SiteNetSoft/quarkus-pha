import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-basic").ariaLabel("Basic table example")
        .column("Name").column("Status").column("Role").actionColumn("Actions")
        .row("John Doe", "Active", "Admin", TableCell.actions(TableAction.link("Edit")))
        .row("Jane Smith", "Inactive", "Editor", TableCell.actions(TableAction.link("Edit")))
        .row("Bob Johnson", "Active", "Viewer", TableCell.actions(TableAction.link("Edit")))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
