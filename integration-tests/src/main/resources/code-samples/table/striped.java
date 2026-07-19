import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-striped").ariaLabel("Striped table example").striped()
        .column("Repository").column("Branches").column("Pull requests").column("Workspaces")
        .row("apollo", "12", "3", "5")
        .row("mercury", "4", "0", "1")
        .row("gemini", "8", "2", "2")
        .row("orion", "21", "5", "9")
        .row("artemis", "3", "1", "0")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
