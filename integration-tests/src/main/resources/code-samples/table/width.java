import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-width").ariaLabel("Table with column width and text modifiers")
        .column(TableColumn.of("Name (20%)").width(20))
        .column(TableColumn.of("Description (30%, truncate)").width(30).truncate())
        .column(TableColumn.of("Role (fit)").fitContent())
        .column(TableColumn.of("Created (nowrap)").nowrap())
        .row("John Doe",
                TableCell.text("A very long description that will be truncated with an ellipsis when it exceeds the column width").withModifier("pf-m-truncate"),
                TableCell.text("Administrator"),
                TableCell.text("Jan 1, 2026").withModifier("pf-m-nowrap"))
        .row("Jane Smith",
                TableCell.text("Another lengthy piece of descriptive text that demonstrates the truncation behaviour of the cell").withModifier("pf-m-truncate"),
                TableCell.text("Editor"),
                TableCell.text("Feb 14, 2026").withModifier("pf-m-nowrap"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
