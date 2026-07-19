import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-clickable-expandable").ariaLabel("Clickable expandable data list").clickable("1")
        .item(DataList.item(DataListCell.title("First row"), "Click selects; the toggle expands").key("1")
                .expandsTo("Expanded details for the first row.").expanded())
        .item(DataList.item(DataListCell.title("Second row"), "The selected row carries pf-m-selected").key("2")
                .expandsTo("Expanded details for the second row."))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
