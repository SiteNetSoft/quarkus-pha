import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-nested-sticky").ariaLabel("Table with nested column headers and a sticky header")
        .stickyHeader().scrollableInnerStyled("max-block-size: 13rem; overflow: auto")
        .column("Name")
        .column(TableColumn.group("Contact", "Email", "Phone"))
        .column(TableColumn.group("Employment", "Role", "Level"))
        .row("John Doe", "john@example.com", "555-0100", "Admin", "Senior")
        .row("Jane Smith", "jane@example.com", "555-0101", "Editor", "Mid")
        .row("Bob Johnson", "bob@example.com", "555-0102", "Viewer", "Junior")
        .row("Amelia Chen", "amelia@example.com", "555-0103", "Admin", "Senior")
        .row("Jordan Blake", "jordan@example.com", "555-0104", "Editor", "Senior")
        .row("Priya Nair", "priya@example.com", "555-0105", "Viewer", "Mid")
        .row("Tomás Rivera", "tomas@example.com", "555-0106", "Editor", "Junior")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
