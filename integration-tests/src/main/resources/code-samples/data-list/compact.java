import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-compact").ariaLabel("Data list").compact()
        .item(DataList.item("user-1@example.com", "Admin"))
        .item(DataList.item("user-2@example.com", "Member"))
        .item(DataList.item("user-3@example.com", "Viewer"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
