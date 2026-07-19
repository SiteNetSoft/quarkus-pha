import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-compact-expandable").ariaLabel("Compact expandable data list").compact()
        .item(DataList.item(DataListCell.title("Compact row 1"), "Starts expanded")
                .expandsTo("Expanded details for Compact row 1.").expanded())
        .item(DataList.item(DataListCell.title("Compact row 2"), "Collapsed by default")
                .expandsTo("Expanded details for Compact row 2."))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
