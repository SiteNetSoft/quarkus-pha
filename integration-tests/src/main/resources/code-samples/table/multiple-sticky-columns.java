import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-multiple-sticky").ariaLabel("Table with multiple sticky columns")
        .scrollableInner("tbl-multiple-sticky-wrapper")
        .column(TableColumn.of("Node")
                .stickyCell("pf-m-left", "--pf-v6-c-table__sticky-cell--MinWidth: 100px")
                .nowrap().rowHeader())
        .column(TableColumn.of("Status")
                .stickyCell("pf-m-left pf-m-border-right", "--pf-v6-c-table__sticky-cell--MinWidth: 80px; --pf-v6-c-table__sticky-cell--InsetInlineStart: 100px")
                .nowrap().rowHeader())
        .column(TableColumn.of("Zone").nowrap())
        .column(TableColumn.of("Kubernetes version").nowrap())
        .column(TableColumn.of("CPU allocatable").nowrap())
        .column(TableColumn.of("Memory allocatable").nowrap())
        .column(TableColumn.of("Pods running").nowrap())
        .column(TableColumn.of("Created").nowrap())
        .column(TableColumn.of("Kernel").nowrap())
        .row("node-a1", "Ready", "us-east-1a", "v1.31.2", "7.5 cores", "28 GiB", "42", "86 days ago", "6.1.0-18")
        .row("node-a2", "Ready", "us-east-1b", "v1.31.2", "7.5 cores", "28 GiB", "38", "86 days ago", "6.1.0-18")
        .row("node-b1", "NotReady", "us-east-1c", "v1.30.6", "15 cores", "60 GiB", "0", "12 days ago", "6.1.0-17")
        .row("node-b2", "Ready", "us-east-1a", "v1.31.2", "15 cores", "60 GiB", "61", "12 days ago", "6.1.0-18")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
