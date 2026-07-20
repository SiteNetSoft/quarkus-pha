import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-vertical-category").vertical().categoryName("Category 1")
        .label(Label.of("Label 1").id("lbg-vcat-1").icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-vcat-2").color("blue").icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-vcat-3").color("green").icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
