import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-rm-cat").categoryName("Group label").closable("Remove category").numLabels(3)
        .label(Label.of("Label 1").id("lbg-rm-1").icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-rm-2").color("blue").icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-rm-3").color("green").icon("fa:circle-info"))
        .label(Label.of("Label 4").id("lbg-rm-4").color("orange").icon("fa:circle-info"))
        .label(Label.of("Label 5").id("lbg-rm-5").color("red").icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
