import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-nested").ariaLabel("Table with nested column headers")
        .column("Name")
        .column(TableColumn.group("Contact", "Email", "Phone"))
        .column(TableColumn.group("Employment", "Role", "Level"))
        .row("John Doe", "john@example.com", "555-0100", "Admin", "Senior")
        .row("Jane Smith", "jane@example.com", "555-0101", "Editor", "Mid")
        .row("Bob Johnson", "bob@example.com", "555-0102", "Viewer", "Junior")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
