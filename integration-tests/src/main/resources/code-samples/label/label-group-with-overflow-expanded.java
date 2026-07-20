import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-overflow-expanded").ariaLabel("Label group with overflow expanded")
        .numLabels(3).startExpanded()
        .label(Label.of("Label one").id("lbg-ofe-1").icon("fa:circle-info"))
        .label(Label.of("Label two").id("lbg-ofe-2").color("blue").icon("fa:circle-info"))
        .label(Label.of("Label three").id("lbg-ofe-3").color("green").icon("fa:circle-info"))
        .label(Label.of("Label four").id("lbg-ofe-4").color("teal").icon("fa:circle-info"))
        .label(Label.of("Label five").id("lbg-ofe-5").color("purple").icon("fa:circle-info"))
        .label(Label.of("Label six").id("lbg-ofe-6").color("orange").icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
