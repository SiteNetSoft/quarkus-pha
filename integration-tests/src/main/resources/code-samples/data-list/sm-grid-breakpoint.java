import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-sm-grid").ariaLabel("Data list").gridBreakpoint("sm")
        .item(DataList.item(DataListCell.title("Grid collapses below sm"), "Cell two", "Cell three"))
        .item(DataList.item(DataListCell.title("Second row"), "Cell two", "Cell three"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
