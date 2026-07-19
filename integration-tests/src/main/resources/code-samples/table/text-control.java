import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
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

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
