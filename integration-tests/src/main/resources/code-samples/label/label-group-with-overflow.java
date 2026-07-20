import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-overflow").ariaLabel("Label group with overflow").numLabels(3)
        .label(Label.of("Label 1").id("lbg-of-1").icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-of-2").color("blue").icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-of-3").color("green").icon("fa:circle-info"))
        .label(Label.of("Label 4").id("lbg-of-4").color("orange").icon("fa:circle-info"))
        .label(Label.of("Label 5").id("lbg-of-5").color("red").icon("fa:circle-info"))
        .label(Label.of("Label 6").id("lbg-of-6").color("purple").icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
