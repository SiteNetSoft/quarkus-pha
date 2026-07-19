import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-expandable").ariaLabel("Data list")
        .item(DataList.item(DataListCell.title("Expandable row 1"), "Toggle reveals details")
                .expandsTo("Expanded details for Expandable row 1.").expanded())
        .item(DataList.item(DataListCell.title("Expandable row 2"), "Collapsed by default")
                .expandsTo("Expanded details for Expandable row 2."))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
