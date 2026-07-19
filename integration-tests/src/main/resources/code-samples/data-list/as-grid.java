import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-as-grid").ariaLabel("Data list as grid").modifier("pf-m-grid")
        .item(DataList.item(DataListCell.title("Project Apollo"), "Backend service", "Active", "2 hours ago"))
        .item(DataList.item(DataListCell.title("Mercury"), "Web frontend", "Active", "5 hours ago"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
