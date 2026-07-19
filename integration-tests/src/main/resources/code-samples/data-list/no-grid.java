import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-no-grid").ariaLabel("Data list with no grid").modifier("pf-m-grid-none")
        .item(DataList.item(DataListCell.title("Project Apollo"), "Backend service", "Active", "2 hours ago"))
        .item(DataList.item(DataListCell.title("Mercury"), "Web frontend", "Active", "5 hours ago"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
