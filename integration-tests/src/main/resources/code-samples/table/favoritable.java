import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-favorite").ariaLabel("Favoritable table")
        .favoriteColumn()
        .column("Name").column("Status").column("Role")
        .row(Table.row("John Doe", "Active", "Admin").favorited())
        .row("Jane Smith", "Inactive", "Editor")
        .row("Bob Johnson", "Active", "Viewer")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
