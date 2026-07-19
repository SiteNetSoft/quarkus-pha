import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-actions").ariaLabel("Data list")
        .item(DataList.item(DataListCell.title("Single action row"), "A lone action button")
                .action(TableAction.secondary("Action")))
        .item(DataList.item(DataListCell.title("Multiple actions row"), "A kebab menu of actions")
                .kebab("Row actions", TableAction.link("Edit"), TableAction.danger("Delete")))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
