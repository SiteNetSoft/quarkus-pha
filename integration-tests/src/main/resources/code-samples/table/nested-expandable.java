import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-nested-expandable").ariaLabel("Table with nested column headers and expandable rows")
        .toggleColumn().checkColumn()
        .column("Name")
        .column(TableColumn.group("Contact", "Email", "Phone"))
        .column("Role")
        .row(Table.row("John Doe", "john@example.com", "555-0100", "Admin")
                .expandsTo("Joined March 2024. Manages the platform and infrastructure teams."))
        .row(Table.row("Jane Smith", "jane@example.com", "555-0101", "Editor")
                .expandsTo("Joined August 2025. Owns the editorial calendar and content review."))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
