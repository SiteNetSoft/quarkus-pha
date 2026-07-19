import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-table-text").ariaLabel("Table text element example").gridMd()
        .caption("Cell content wrapped in the pf-v6-c-table__text element")
        .column("Selector").column("Result")
        .row(TableCell.text("td.pf-m-truncate").asRowHeader().withModifier("pf-m-fit-content").asTableText(),
                TableCell.text("This cell contains a single table text wrapper and the cell applies pf-m-truncate, so the text truncates in table layout and stays controlled in grid layout.")
                        .withModifier("pf-m-truncate").asTableText())
        .row(TableCell.text("pf-v6-l-stack").asRowHeader().withModifier("pf-m-fit-content").asTableText(),
                TableCell.textStack(
                        TableCell.text("Multiple elements in a cell should be wrapped with a div or a PatternFly layout so the table's grid fallback doesn't stack them unexpectedly."),
                        TableCell.link("http://truncatemodifierappliedtoaverylongurlthatwillforcethetabletobreakluckilywehavethetabletextelement.com", "#")
                                .withModifier("pf-m-truncate")))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
