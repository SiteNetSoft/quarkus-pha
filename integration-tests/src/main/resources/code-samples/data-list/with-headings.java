import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-with-headings").ariaLabel("Data list with headings")
        .item(DataList.item(DataListCell.heading("Primary content"), "Secondary content"))
        .item(DataList.item(
                DataListCell.heading("Secondary content (pf-m-no-fill)").withModifier("pf-m-no-fill"),
                DataListCell.text("Secondary content (pf-m-align-right pf-m-no-fill)")
                        .withModifier("pf-m-no-fill").withModifier("pf-m-align-right")))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
