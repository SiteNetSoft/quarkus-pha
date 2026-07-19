import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-width-modifiers").ariaLabel("Data list")
        .item(DataList.item(DataListCell.title("default"),
                DataListCell.text("pf-m-flex-2 takes twice the space").withModifier("pf-m-flex-2"),
                DataListCell.text("pf-m-flex-3").withModifier("pf-m-flex-3")))
        .item(DataList.item(DataListCell.title("default"),
                DataListCell.text("pf-m-flex-4 takes four times the space").withModifier("pf-m-flex-4")))
        .item(DataList.item(DataListCell.title("default"),
                DataListCell.text("pf-m-flex-5 takes five times the space").withModifier("pf-m-flex-5")))
        .item(DataList.item(DataListCell.text("pf-m-no-fill keeps to its content").withModifier("pf-m-no-fill"),
                "default fills the remaining space"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
