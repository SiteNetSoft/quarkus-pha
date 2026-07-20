import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-vertical").vertical().categoryName("Group label with a very long name")
        .closable("Remove category").numLabels(3)
        .label(Label.of("Label 1").id("lbg-v-1").icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-v-2").color("blue").icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-v-3").color("green").icon("fa:circle-info"))
        .label(Label.of("Label 4").id("lbg-v-4").color("orange").icon("fa:circle-info"))
        .label(Label.of("Label 5").id("lbg-v-5").color("red").icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
