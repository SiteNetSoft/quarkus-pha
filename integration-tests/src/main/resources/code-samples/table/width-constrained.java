import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
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

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
