import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-footer").ariaLabel("Table with a footer")
        .column("Name").column("Status").column("Role")
        .row("John Doe", "Active", "Admin")
        .row("Jane Smith", "Inactive", "Editor")
        .row("Bob Johnson", "Active", "Viewer")
        .footer(TableCell.text("Total users").asRowHeader(), TableCell.text("3").withColspan(2))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
