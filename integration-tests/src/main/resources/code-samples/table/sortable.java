import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-sortable").ariaLabel("Sortable table example")
        .sortEndpoint("/api/htmx/table-sort")
        .column(TableColumn.of("Name").sortable("name").sorted(true))
        .column(TableColumn.of("Role").sortable("role"))
        .column(TableColumn.of("Region").sortable("region"))
        .row("Alice Chen", "Admin", "us-east-1")
        .row("Bob Johnson", "Viewer", "eu-west-1")
        .row("Diego Ramos", "Viewer", "ap-southeast-1")
        .row("Jane Smith", "Editor", "eu-west-1")
        .row("John Doe", "Admin", "us-west-2")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
