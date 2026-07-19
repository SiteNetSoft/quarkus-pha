import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
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

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
