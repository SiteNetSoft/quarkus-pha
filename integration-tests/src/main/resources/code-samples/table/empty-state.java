import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-empty").ariaLabel("Table with no results")
        .column("Name").column("Status").column("Role")
        .row(TableCell.emptyState("fa:magnifying-glass", "No results found",
                "No results match the filter criteria. Clear all filters and try again."))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
