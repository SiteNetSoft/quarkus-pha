import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-mixed-expandable").ariaLabel("Data list")
        .item(DataList.item(DataListCell.title("Expandable row"), "Has a toggle and details")
                .expandsTo("Expanded details for the expandable row."))
        .item(DataList.item(DataListCell.title("Plain row"), "No toggle, no expandable content"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
