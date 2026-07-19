import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-sticky").ariaLabel("Table with sticky header and first column")
        .stickyHeader().scrollable("max-block-size: 13rem; overflow: auto")
        .column(TableColumn.of("Repository")
                .stickyCell("pf-m-left", "--pf-v6-c-table__sticky-cell--MinWidth: 10rem; --pf-v6-c-table__sticky-cell--m-left--InsetInlineStart: 0")
                .rowHeader())
        .column(TableColumn.of("Branches").nowrap())
        .column(TableColumn.of("Pull requests").nowrap())
        .column(TableColumn.of("Workspaces").nowrap())
        .column(TableColumn.of("Last commit").nowrap())
        .column(TableColumn.of("Contributors").nowrap())
        .column(TableColumn.of("Open issues").nowrap())
        .column(TableColumn.of("Size").nowrap())
        .row("api-gateway", "12 branches", "4 open", "2 active", "2 hours ago", "18 people", "7 issues", "1.2 GB")
        .row("payments", "8 branches", "6 open", "3 active", "5 hours ago", "11 people", "14 issues", "0.9 GB")
        .row("search", "5 branches", "1 open", "1 active", "1 day ago", "6 people", "3 issues", "2.4 GB")
        .row("notifications", "9 branches", "3 open", "2 active", "3 hours ago", "9 people", "5 issues", "0.4 GB")
        .row("billing", "7 branches", "2 open", "1 active", "6 hours ago", "8 people", "9 issues", "1.1 GB")
        .row("analytics", "14 branches", "8 open", "4 active", "30 min ago", "22 people", "18 issues", "3.8 GB")
        .row("auth", "6 branches", "0 open", "1 active", "2 days ago", "5 people", "2 issues", "0.6 GB")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
