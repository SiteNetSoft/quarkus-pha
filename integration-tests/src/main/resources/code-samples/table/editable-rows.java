import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-editable").ariaLabel("Editable table")
        .column("Name").column("Role").inlineEditColumn("Edit")
        .row("John Doe", "Admin")
        .row("Jane Smith", "Editor")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
