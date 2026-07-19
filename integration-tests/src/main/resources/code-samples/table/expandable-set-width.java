import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-expandable-set-width").ariaLabel("Expandable table with set width columns")
        .gridMd()
        .toggleColumn()
        .column(TableColumn.of("Incident").width(30))
        .column(TableColumn.of("Severity").width(20))
        .column("Opened")
        .row(Table.row("INC-2041 — checkout latency spike", "Major", "09:14")
                .expandsTo("Root cause traced to a cache stampede after the 09:05 deploy. Mitigated by rolling back.")
                .detailAriaLabel("Toggle details for INC-2041"))
        .row(Table.row("INC-2042 — stale search results", "Minor", "11:47")
                .expandsTo("Indexer lag reached 40 minutes; queue drained after scaling consumers from 2 to 6.")
                .detailAriaLabel("Toggle details for INC-2042"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
