import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-plain").ariaLabel("Data list").plain()
        .item(DataList.item(DataListCell.title("Primary content"), "Secondary content"))
        .item(DataList.item(DataListCell.title("Second row"), "More content"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
