import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-clickable-rows").ariaLabel("Data list").clickable("1")
        .item(DataList.item(DataListCell.title("First row"), "Click or press Enter to select").key("1"))
        .item(DataList.item(DataListCell.title("Second row"), "The selected row carries pf-m-selected").key("2"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
