import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-borderless-compact").ariaLabel("Borderless compact table example")
        .borderless().compact().gridMd()
        .column("Contact").column("Team").column("Location").column("Last seen")
        .row("Amelia Chen", "Platform", "Boston", "5 minutes ago")
        .row("Jordan Blake", "Commerce", "Toronto", "1 hour ago")
        .row("Priya Nair", "Discovery", "Bengaluru", "Yesterday")
        .row("Tomás Rivera", "Design", "Madrid", "3 days ago")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
