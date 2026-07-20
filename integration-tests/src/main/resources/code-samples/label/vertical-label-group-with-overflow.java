import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-vertical-overflow").vertical().ariaLabel("Vertical label group with overflow").numLabels(3)
        .label(Label.of("Label 1").id("lbg-vof-1").icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-vof-2").color("blue").icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-vof-3").color("green").icon("fa:circle-info"))
        .label(Label.of("Label 4").id("lbg-vof-4").color("teal").icon("fa:circle-info"))
        .label(Label.of("Label 5").id("lbg-vof-5").color("purple").icon("fa:circle-info"))
        .label(Label.of("Label 6").id("lbg-vof-6").color("orange").icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
