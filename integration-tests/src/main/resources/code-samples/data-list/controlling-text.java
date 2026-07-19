import org.sitenetsoft.quarkus.pha.model.*;

DataList list = DataList.builder()
        .id("dl-controlling-text").ariaLabel("Data list")
        .item(DataList.item(
                DataListCell.text("pf-m-break-word").withModifier("pf-m-break-word").id("dl-text-break"),
                DataListCell.text("ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-")
                        .withModifier("pf-m-break-word")))
        .item(DataList.item(
                DataListCell.text("pf-m-truncate").withModifier("pf-m-truncate").id("dl-text-truncate"),
                DataListCell.text("ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-")
                        .withModifier("pf-m-truncate")))
        .item(DataList.item(
                DataListCell.text("pf-m-nowrap").withModifier("pf-m-nowrap").id("dl-text-nowrap"),
                DataListCell.text("This cell refuses to wrap its content onto a second line.")
                        .withModifier("pf-m-nowrap")))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/data-list list=list /}
