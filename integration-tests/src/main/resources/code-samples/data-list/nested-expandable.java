import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-nested-expandable").ariaLabel("Nested expandable data list")
        .item(DataList.item(
                DataListCell.icon("fa:code-branch"),
                DataListCell.title("Outer row 1").description("Contains a nested data list"),
                "Secondary content")
                .expandsToList(nestedInnerList()).expanded())
        .item(DataList.item(
                DataListCell.icon("fa:code-branch"),
                DataListCell.title("Outer row 2"),
                "Plain expandable details")
                .expandsTo("Outer row 2 expanded content."))
        .build();

private static DataList nestedInnerList() {
    return DataList.builder()
            .id("dl-nested-expandable-inner").ariaLabel("Nested data list")
            .item(DataList.item("Nested row 1").expandsTo("Nested row 1 expanded content.").expanded())
            .item(DataList.item("Nested row 2").expandsTo("Nested row 2 expanded content."))
            .item(DataList.item("Nested row 3").expandsTo("Nested row 3 expanded content."))
            .build();
}

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
