import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-vertical-overflow-expanded").vertical()
        .ariaLabel("Vertical label group with overflow expanded").numLabels(3).startExpanded()
        .label(Label.of("Label 1").id("lbg-vofe-1").icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-vofe-2").color("blue").icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-vofe-3").color("green").icon("fa:circle-info"))
        .label(Label.of("Label 4").id("lbg-vofe-4").color("teal").icon("fa:circle-info"))
        .label(Label.of("Label 5").id("lbg-vofe-5").color("purple").icon("fa:circle-info"))
        .label(Label.of("Label 6").id("lbg-vofe-6").color("orange").icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
