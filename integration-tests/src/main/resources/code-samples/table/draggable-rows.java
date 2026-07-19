import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-draggable").ariaLabel("Draggable rows table")
        .dragColumn()
        .column("Name").column("Status").column("Role")
        .row("John Doe", "Active", "Admin")
        .row("Jane Smith", "Inactive", "Editor")
        .row("Bob Johnson", "Active", "Viewer")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
