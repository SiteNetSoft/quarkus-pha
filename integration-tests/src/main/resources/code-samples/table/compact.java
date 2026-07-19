import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-compact").ariaLabel("Compact table example").compact()
        .column("Name").column("Status").column("Role").column("Last seen")
        .row("John Doe", "Active", "Admin", "2 minutes ago")
        .row("Jane Smith", "Inactive", "Editor", "3 days ago")
        .row("Bob Johnson", "Active", "Viewer", "1 hour ago")
        .row("Alice Chen", "Active", "Admin", "just now")
        .row("Diego Ramos", "Pending", "Viewer", "never")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
