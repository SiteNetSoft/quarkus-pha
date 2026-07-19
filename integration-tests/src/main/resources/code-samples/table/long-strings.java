import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-long-strings").ariaLabel("Table with long strings in the content").dataLabels()
        .column("Repository").column("Branches").column("Pull requests").column("Workspaces").column("Last commit")
        .row("Long lines of text will shrink adjacent column widths.", "10", "25", "5", "2 days ago")
        .row("This example is not responsive. Adjacent tbody cells will shrink because this text is a longer string while adjacent text is short. Truncation can be overridden on th cells with pf-m-wrap, pf-m-nowrap or pf-m-fit-content.",
                "10", "25", "5", "2 days ago")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
