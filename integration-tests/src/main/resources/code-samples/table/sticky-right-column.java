import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-sticky-right").ariaLabel("Table with a sticky right column")
        .scrollableInner("tbl-sticky-right-wrapper")
        .column(TableColumn.of("Node").nowrap())
        .column(TableColumn.of("Zone").nowrap())
        .column(TableColumn.of("Kubernetes version").nowrap())
        .column(TableColumn.of("CPU allocatable").nowrap())
        .column(TableColumn.of("Memory allocatable").nowrap())
        .column(TableColumn.of("Pods running").nowrap())
        .column(TableColumn.of("Created").nowrap())
        .column(TableColumn.of("Kernel").nowrap())
        .column(TableColumn.of("Status")
                .stickyCell("pf-m-right pf-m-border-left", "--pf-v6-c-table__sticky-cell--MinWidth: 120px")
                .nowrap().rowHeader())
        .row("node-a1", "us-east-1a", "v1.31.2", "7.5 cores", "28 GiB", "42", "86 days ago", "6.1.0-18", "Ready")
        .row("node-a2", "us-east-1b", "v1.31.2", "7.5 cores", "28 GiB", "38", "86 days ago", "6.1.0-18", "Ready")
        .row("node-b1", "us-east-1c", "v1.30.6", "15 cores", "60 GiB", "0", "12 days ago", "6.1.0-17", "NotReady")
        .row("node-b2", "us-east-1a", "v1.31.2", "15 cores", "60 GiB", "61", "12 days ago", "6.1.0-18", "Ready")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
