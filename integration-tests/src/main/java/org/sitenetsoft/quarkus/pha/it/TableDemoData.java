package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Table;
import org.sitenetsoft.quarkus.pha.model.TableAction;
import org.sitenetsoft.quarkus.pha.model.TableBody;
import org.sitenetsoft.quarkus.pha.model.TableCell;
import org.sitenetsoft.quarkus.pha.model.TableColumn;

/**
 * Demo data for the table examples — every model-driven example on
 * /components/table is populated from one of these Table models. Globals so
 * the standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/table/ served
 * on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class TableDemoData {

    public static Table demoBasicTable = Table.builder()
            .id("tbl-basic").ariaLabel("Basic table example")
            .column("Name").column("Status").column("Role").actionColumn("Actions")
            .row("John Doe", "Active", "Admin", TableCell.actions(TableAction.link("Edit")))
            .row("Jane Smith", "Inactive", "Editor", TableCell.actions(TableAction.link("Edit")))
            .row("Bob Johnson", "Active", "Viewer", TableCell.actions(TableAction.link("Edit")))
            .build();

    public static Table demoCompactTable = Table.builder()
            .id("tbl-compact").ariaLabel("Compact table example").compact()
            .column("Name").column("Status").column("Role").column("Last seen")
            .row("John Doe", "Active", "Admin", "2 minutes ago")
            .row("Jane Smith", "Inactive", "Editor", "3 days ago")
            .row("Bob Johnson", "Active", "Viewer", "1 hour ago")
            .row("Alice Chen", "Active", "Admin", "just now")
            .row("Diego Ramos", "Pending", "Viewer", "never")
            .build();

    public static Table demoBorderlessTable = Table.builder()
            .id("tbl-borderless").ariaLabel("Borderless table example").borderless()
            .column("Service").column("Region").column("Uptime")
            .row("api-gateway", "us-east-1", "99.99%")
            .row("payments", "eu-west-1", "99.97%")
            .row("search", "ap-southeast-1", "99.95%")
            .row("notifications", "us-west-2", "99.92%")
            .build();

    public static Table demoBorderlessCompactTable = Table.builder()
            .id("tbl-borderless-compact").ariaLabel("Borderless compact table example")
            .borderless().compact().gridMd()
            .column("Contact").column("Team").column("Location").column("Last seen")
            .row("Amelia Chen", "Platform", "Boston", "5 minutes ago")
            .row("Jordan Blake", "Commerce", "Toronto", "1 hour ago")
            .row("Priya Nair", "Discovery", "Bengaluru", "Yesterday")
            .row("Tomás Rivera", "Design", "Madrid", "3 days ago")
            .build();

    public static Table demoStripedTable = Table.builder()
            .id("tbl-striped").ariaLabel("Striped table example").striped()
            .column("Repository").column("Branches").column("Pull requests").column("Workspaces")
            .row("apollo", "12", "3", "5")
            .row("mercury", "4", "0", "1")
            .row("gemini", "8", "2", "2")
            .row("orion", "21", "5", "9")
            .row("artemis", "3", "1", "0")
            .build();

    public static Table demoStripedTrTable = Table.builder()
            .id("tbl-striped-tr").ariaLabel("Striped table with manually striped rows").gridMd()
            .caption("Rows striped one by one")
            .column("Repository").column("Branches").column("Pull requests").column("Workspaces")
            .row(Table.row("apollo", "12", "3", "5").stripedRow())
            .row("mercury", "4", "0", "1")
            .row(Table.row("gemini", "8", "2", "2").stripedRow())
            .row("orion", "21", "5", "9")
            .build();

    public static Table demoStripedTbodyTable = Table.builder()
            .id("tbl-striped-tbody").ariaLabel("Striped table with multiple tbody elements").gridMd()
            .caption("Production and staging deployments")
            .column("Service").column("Version").column("Replicas").column("Status")
            .body(TableBody.Stripe.ODD)
            .row("api-gateway (prod)", "2.14.0", "6", "Running")
            .row("payments (prod)", "1.9.2", "4", "Running")
            .row("search (prod)", "3.1.0", "3", "Running")
            .body(TableBody.Stripe.EVEN)
            .row("api-gateway (staging)", "2.15.0-rc.1", "2", "Running")
            .row("payments (staging)", "1.10.0-rc.2", "1", "Crash-looping")
            .row("search (staging)", "3.2.0-rc.1", "1", "Running")
            .build();

    public static Table demoPlainTable = Table.builder()
            .id("tbl-plain").ariaLabel("Plain table example").gridMd().modifier("pf-m-plain")
            .caption("Q2 releases by service")
            .column("Service").column("Version").column("Released").column("Owner")
            .row("api-gateway", "2.14.0", "Apr 3", "Platform")
            .row("payments", "1.9.2", "May 12", "Commerce")
            .row("search", "3.1.0", "Jun 20", "Discovery")
            .build();

    public static Table demoFooterTable = Table.builder()
            .id("tbl-footer").ariaLabel("Table with a footer")
            .column("Name").column("Status").column("Role")
            .row("John Doe", "Active", "Admin")
            .row("Jane Smith", "Inactive", "Editor")
            .row("Bob Johnson", "Active", "Viewer")
            .footer(TableCell.text("Total users").asRowHeader(), TableCell.text("3").withColspan(2))
            .build();

    public static Table demoWidthTable = Table.builder()
            .id("tbl-width").ariaLabel("Table with column width and text modifiers")
            .column(TableColumn.of("Name (20%)").width(20))
            .column(TableColumn.of("Description (30%, truncate)").width(30).truncate())
            .column(TableColumn.of("Role (fit)").fitContent())
            .column(TableColumn.of("Created (nowrap)").nowrap())
            .row("John Doe",
                    TableCell.text("A very long description that will be truncated with an ellipsis when it exceeds the column width").withModifier("pf-m-truncate"),
                    TableCell.text("Administrator"),
                    TableCell.text("Jan 1, 2026"))
            .row("Jane Smith",
                    TableCell.text("Another lengthy piece of descriptive text that demonstrates the truncation behaviour of the cell").withModifier("pf-m-truncate"),
                    TableCell.text("Editor"),
                    TableCell.text("Feb 14, 2026"))
            .build();

    public static Table demoWidthConstrainedTable = Table.builder()
            .id("tbl-width-constrained").ariaLabel("Width constrained table example").gridMd()
            .column(TableColumn.of("Width 40").width(40))
            .column("Branches").column("Pull requests")
            .column(TableColumn.of("Fit content th").fitContent())
            .column("Last commit")
            .row("Since this is a long string of text and the other cells contain short strings, the header's width is capped at 40% so it cannot crowd out the other columns.",
                    "10", "25", "5", "2 days ago")
            .body(TableBody.Stripe.NONE)
            .row(TableCell.text("This string will truncate in table mode only. It is just as long as the one above, but pf-m-truncate clips it to the column width instead of wrapping.").withModifier("pf-m-truncate"),
                    TableCell.text("10"), TableCell.text("25"), TableCell.text("5"), TableCell.text("2 days ago"))
            .build();

    public static Table demoLongStringsTable = Table.builder()
            .id("tbl-long-strings").ariaLabel("Table with long strings in the content").dataLabels()
            .column("Repository").column("Branches").column("Pull requests").column("Workspaces").column("Last commit")
            .row("Long lines of text will shrink adjacent column widths.", "10", "25", "5", "2 days ago")
            .row("This example is not responsive. Adjacent tbody cells will shrink because this text is a longer string while adjacent text is short. Truncation can be overridden on th cells with pf-m-wrap, pf-m-nowrap or pf-m-fit-content.",
                    "10", "25", "5", "2 days ago")
            .build();

    public static Table demoBreakpointsTable = Table.builder()
            .id("tbl-breakpoints").ariaLabel("Table with hidden and visible breakpoint modifiers").dataLabels()
            .column(TableColumn.of("Repository").withModifier("pf-m-hidden").withModifier("pf-m-visible-on-md").withModifier("pf-m-hidden-on-lg"))
            .column("Branches")
            .column(TableColumn.of("Pull requests").withModifier("pf-m-hidden-on-md").withModifier("pf-m-visible-on-lg"))
            .column("Workspaces")
            .column(TableColumn.of("Last commit").withModifier("pf-m-hidden").withModifier("pf-m-visible-on-sm"))
            .row("apollo", "12", "3", "5", "2 days ago")
            .row("mercury", "4", "0", "1", "5 hours ago")
            .row("gemini", "8", "2", "2", "1 hour ago")
            .build();

    public static Table demoSortableTable = Table.builder()
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

    public static Table demoSelectCheckTable = Table.builder()
            .id("tbl-select-check").ariaLabel("Selectable table with checkboxes")
            .checkColumn()
            .column("Name").column("Status").column("Role")
            .row("John Doe", "Active", "Admin")
            .row(Table.row("Jane Smith", "Inactive", "Editor").checkedRow())
            .row("Bob Johnson", "Active", "Viewer")
            .build();

    public static Table demoSelectRadioTable = Table.builder()
            .id("tbl-select-radio").ariaLabel("Selectable table with radio buttons")
            .checkColumn().radioSelection()
            .column("Name").column("Status").column("Role")
            .row("John Doe", "Active", "Admin")
            .row(Table.row("Jane Smith", "Inactive", "Editor").checkedRow())
            .row("Bob Johnson", "Active", "Viewer")
            .build();

    public static Table demoClickableTable = Table.builder()
            .id("tbl-clickable").ariaLabel("Table with selectable rows").gridMd()
            .clickable("jane")
            .column("Name").column("Status").column("Role")
            .row(Table.row("John Doe", "Active", "Admin").key("john"))
            .row(Table.row("Jane Smith", "Inactive", "Editor").key("jane"))
            .row(Table.row("Bob Johnson", "Active", "Viewer").key("bob"))
            .build();

    public static Table demoExpandableTable = Table.builder()
            .id("tbl-expandable").ariaLabel("Expandable table example")
            .toggleColumn()
            .column("Name").column("Status").column("Region")
            .row(Table.row("api-gateway", "Healthy", "us-east-1")
                    .expandsTo("12 healthy instances across 3 availability zones. Average latency 42ms. Last deployed 4 hours ago.")
                    .paragraphDetail().detailAriaLabel("Toggle row details for api-gateway"))
            .row(Table.row("payments", "Degraded", "eu-west-1")
                    .expandsTo("8 healthy instances, 1 unhealthy. P99 latency 320ms (threshold 250ms). Oncall has been paged.")
                    .paragraphDetail().detailAriaLabel("Toggle row details for payments"))
            .row(Table.row("search", "Healthy", "ap-southeast-1")
                    .expandsTo("4 healthy instances. Index size 2.4 GB across 16 shards. No alerts.")
                    .paragraphDetail().detailAriaLabel("Toggle row details for search"))
            .build();

    public static Table demoCompactExpandableTable = Table.builder()
            .id("tbl-compact-expandable").ariaLabel("Compact expandable table example")
            .compact().gridMd()
            .toggleColumn()
            .column("Pipeline").column("Last run").column("Duration")
            .row(Table.row("build-frontend", "Passed", "4m 12s")
                    .expandsTo("312 tests, 0 failures. Artifacts published to the snapshot repository."))
            .row(Table.row("build-backend", "Failed", "7m 45s")
                    .expandsTo("2 integration tests failed in the persistence module; see run #482 for logs."))
            .row(Table.row("nightly-e2e", "Passed", "28m 03s")
                    .expandsTo("Full browser matrix green. Flake rate 0.4% over the last 30 runs."))
            .build();

    public static Table demoBorderlessExpandableTable = Table.builder()
            .id("tbl-borderless-expandable").ariaLabel("Borderless expandable table example")
            .borderless().gridMd()
            .toggleColumn()
            .column("Dataset").column("Rows").column("Updated")
            .row(Table.row("orders-2026", "1.2M", "Hourly")
                    .expandsTo("Partitioned by order date; retained for 24 months. Sourced from the checkout event stream."))
            .row(Table.row("customers", "840K", "Daily")
                    .expandsTo("Deduplicated nightly against the CRM export; PII columns are masked outside production."))
            .build();

    public static Table demoStripedExpandableTable = Table.builder()
            .id("tbl-striped-expandable").ariaLabel("Striped expandable table example")
            .striped().gridMd()
            .toggleColumn()
            .column("Backup job").column("Schedule").column("Last result")
            .row(Table.row("postgres-daily", "02:00 UTC", "Succeeded")
                    .expandsTo("42 GB snapshot in 11 minutes; verified restore on the standby cluster."))
            .row(Table.row("object-store-weekly", "Sun 04:00 UTC", "Succeeded")
                    .expandsTo("1.8 TB incremental to cold storage; 97% deduplication ratio."))
            .row(Table.row("config-hourly", "Hourly", "Failed")
                    .expandsTo("Vault token expired at 13:00; renewal issued and the 14:00 run recovered."))
            .build();

    public static Table demoAnimatedExpandableTable = Table.builder()
            .id("tbl-animated-expandable").ariaLabel("Animated expandable table example")
            .gridMd().modifier("pf-m-animate-expand")
            .toggleColumn()
            .column("Service").column("Status").column("Region")
            .row(Table.row("api-gateway", "Healthy", "us-east-1")
                    .expandsTo("12 healthy instances across 3 availability zones. Average latency 42ms.")
                    .expanded())
            .row(Table.row("payments", "Degraded", "eu-west-1")
                    .expandsTo("8 healthy instances, 1 unhealthy. P99 latency 320ms (threshold 250ms)."))
            .row(Table.row("search", "Healthy", "ap-southeast-1")
                    .expandsTo("Expandable row content has no padding.")
                    .noPaddingDetail())
            .build();

    public static Table demoExpandableSetWidthTable = Table.builder()
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

    public static Table demoActionsTable = Table.builder()
            .id("tbl-actions").ariaLabel("Table with row actions")
            .column("Name").column("Status").column("Role").actionColumn()
            .row("John Doe", "Active", "Admin",
                    TableCell.kebab("John Doe actions", "Edit", "Delete"))
            .row("Jane Smith", "Inactive", "Editor",
                    TableCell.kebab("Jane Smith actions", "Edit", "Delete"))
            .row("Bob Johnson", "Active", "Viewer",
                    TableCell.actions(TableAction.secondary("Edit"), TableAction.danger("Delete")))
            .build();

    public static Table demoStickyTable = Table.builder()
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

    public static Table demoStickyFooterTable = Table.builder()
            .id("tbl-sticky-footer").ariaLabel("Table with a sticky footer")
            .stickyFooter().scrollable("max-block-size: 13rem; overflow: auto")
            .column("Invoice").column("Customer").column("Amount")
            .row("INV-1041", "Acme Corp", "$1,200")
            .row("INV-1042", "Globex", "$860")
            .row("INV-1043", "Initech", "$2,430")
            .row("INV-1044", "Umbrella", "$540")
            .row("INV-1045", "Stark Industries", "$3,100")
            .row("INV-1046", "Wayne Enterprises", "$1,780")
            .row("INV-1047", "Hooli", "$920")
            .row("INV-1048", "Vandelay", "$610")
            .footer(TableCell.text("Total").asRowHeader().withColspan(2), TableCell.text("$11,440"))
            .build();

    public static Table demoMultipleStickyTable = Table.builder()
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

    public static Table demoStickyRightTable = Table.builder()
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

    public static Table demoNestedHeadersTable = Table.builder()
            .id("tbl-nested").ariaLabel("Table with nested column headers")
            .column("Name")
            .column(TableColumn.group("Contact", "Email", "Phone"))
            .column(TableColumn.group("Employment", "Role", "Level"))
            .row("John Doe", "john@example.com", "555-0100", "Admin", "Senior")
            .row("Jane Smith", "jane@example.com", "555-0101", "Editor", "Mid")
            .row("Bob Johnson", "bob@example.com", "555-0102", "Viewer", "Junior")
            .build();

    public static Table demoNestedStickyTable = Table.builder()
            .id("tbl-nested-sticky").ariaLabel("Table with nested column headers and a sticky header")
            .stickyHeader().scrollableInnerStyled("max-block-size: 13rem; overflow: auto")
            .column("Name")
            .column(TableColumn.group("Contact", "Email", "Phone"))
            .column(TableColumn.group("Employment", "Role", "Level"))
            .row("John Doe", "john@example.com", "555-0100", "Admin", "Senior")
            .row("Jane Smith", "jane@example.com", "555-0101", "Editor", "Mid")
            .row("Bob Johnson", "bob@example.com", "555-0102", "Viewer", "Junior")
            .row("Amelia Chen", "amelia@example.com", "555-0103", "Admin", "Senior")
            .row("Jordan Blake", "jordan@example.com", "555-0104", "Editor", "Senior")
            .row("Priya Nair", "priya@example.com", "555-0105", "Viewer", "Mid")
            .row("Tomás Rivera", "tomas@example.com", "555-0106", "Editor", "Junior")
            .build();

    public static Table demoTextControlTable = Table.builder()
            .id("tbl-text-control").ariaLabel("Text control modifiers example").modifier("pf-m-grid-lg").dataLabels()
            .column(TableColumn.of("Truncate (width 20%)").width(20))
            .column("Break word")
            .column(TableColumn.of("Wrapping table header text. This th text will wrap instead of truncate.").wrap())
            .column(TableColumn.of("Fit content").fitContent())
            .column("No wrap")
            .row(TableCell.text("This text will truncate instead of wrap in table layout and wrap gracefully in grid layout.").withModifier("pf-m-truncate"),
                    TableCell.link("http://thisisaverylongurlthatneedstobreakusethebreakwordmodifier.org", "#").withModifier("pf-m-break-word"),
                    TableCell.text("By default, thead cells will truncate and tbody cells will wrap. Use pf-m-wrap on a th to change its behavior."),
                    TableCell.text("This cell's content will adjust itself to the parent th width. This modifier only affects table layouts."),
                    TableCell.link("No wrap", "#").withModifier("pf-m-nowrap"))
            .build();
}
