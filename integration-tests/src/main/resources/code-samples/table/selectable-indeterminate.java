import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-select-indeterminate").ariaLabel("Selectable table with indeterminate select-all")
        .checkColumn().indeterminateSelection()
        .column("Name").column("Status").column("Role")
        .row(Table.row("John Doe", "Active", "Admin").key("john"))
        .row(Table.row("Jane Smith", "Inactive", "Editor").key("jane").checkedRow())
        .row(Table.row("Bob Johnson", "Active", "Viewer").key("bob"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
