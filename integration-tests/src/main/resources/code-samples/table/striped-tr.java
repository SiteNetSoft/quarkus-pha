import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-striped-tr").ariaLabel("Striped table with manually striped rows").gridMd()
        .caption("Rows striped one by one")
        .column("Repository").column("Branches").column("Pull requests").column("Workspaces")
        .row(Table.row("apollo", "12", "3", "5").stripedRow())
        .row("mercury", "4", "0", "1")
        .row(Table.row("gemini", "8", "2", "2").stripedRow())
        .row("orion", "21", "5", "9")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
