import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-checkboxes-actions").ariaLabel("Data list")
        .item(DataList.item(DataListCell.title("Row with checkbox"), "Status: active", "Updated 2 hours ago")
                .checkbox("dl-ca-1-check")
                .action(TableAction.secondary("Action")))
        .item(DataList.item(DataListCell.title("Second row"), "Status: paused", "Updated yesterday")
                .checkbox("dl-ca-2-check")
                .action(TableAction.secondary("Action")))
        .item(DataList.item(DataListCell.title("Responsive actions row"),
                "Buttons at the lg breakpoint and up", "A kebab menu below it")
                .checkbox("dl-ca-3-check")
                .responsiveActions("Row actions",
                        TableAction.primary("Primary"), TableAction.secondary("Secondary")))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
