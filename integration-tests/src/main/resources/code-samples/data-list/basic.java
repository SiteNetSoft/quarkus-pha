import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-basic").ariaLabel("Data list")
        .item(DataList.item(DataListCell.title("Project Apollo").description("Backend service"),
                "Active", "2 hours ago"))
        .item(DataList.item(DataListCell.title("Mercury").description("Web frontend"),
                "Active", "5 hours ago"))
        .item(DataList.item(DataListCell.title("Gemini").description("Data pipeline"),
                "Paused", "Yesterday"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
