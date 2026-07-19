import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-actions").ariaLabel("Table with row actions")
        .column("Name").column("Status").column("Role").actionColumn()
        .row("John Doe", "Active", "Admin",
                TableCell.kebab("John Doe actions", "Edit", "Delete"))
        .row("Jane Smith", "Inactive", "Editor",
                TableCell.kebab("Jane Smith actions", "Edit", "Delete"))
        .row("Bob Johnson", "Active", "Viewer",
                TableCell.actions(TableAction.secondary("Edit"), TableAction.danger("Delete")))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
