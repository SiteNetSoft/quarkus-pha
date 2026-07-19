import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-favorites-sortable").ariaLabel("Favoritable table sortable by favorites")
        .favoriteColumn().favoritesSortable()
        .column("Name").column("Status").column("Role")
        .row(Table.row("John Doe", "Active", "Admin").key("john").favorited())
        .row(Table.row("Jane Smith", "Inactive", "Editor").key("jane"))
        .row(Table.row("Bob Johnson", "Active", "Viewer").key("bob"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
