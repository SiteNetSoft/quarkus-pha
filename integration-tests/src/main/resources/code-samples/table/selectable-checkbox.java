import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-select-check").ariaLabel("Selectable table with checkboxes")
        .checkColumn()
        .column("Name").column("Status").column("Role")
        .row("John Doe", "Active", "Admin")
        .row(Table.row("Jane Smith", "Inactive", "Editor").checkedRow())
        .row("Bob Johnson", "Active", "Viewer")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
