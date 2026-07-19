import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-select-radio").ariaLabel("Selectable table with radio buttons")
        .checkColumn().radioSelection()
        .column("Name").column("Status").column("Role")
        .row("John Doe", "Active", "Admin")
        .row(Table.row("Jane Smith", "Inactive", "Editor").checkedRow())
        .row("Bob Johnson", "Active", "Viewer")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
