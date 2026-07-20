import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-vertical-category-removable").vertical().categoryName("Category 1")
        .closable("Close vertical label group")
        .label(Label.of("Label 1").id("lbg-vcatr-1").icon("fa:circle-info")
                .removable("$el.closest('li').remove()"))
        .label(Label.of("Label 2").id("lbg-vcatr-2").color("blue").icon("fa:circle-info")
                .removable("$el.closest('li').remove()"))
        .label(Label.of("Label 3").id("lbg-vcatr-3").color("green").icon("fa:circle-info")
                .removable("$el.closest('li').remove()"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
