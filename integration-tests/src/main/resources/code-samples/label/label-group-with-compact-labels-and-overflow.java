import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-compact-overflow").ariaLabel("Label group with compact labels and overflow").numLabels(3)
        .label(Label.of("Label 1").id("lbg-co-1").asCompact().icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-co-2").color("blue").asCompact().icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-co-3").color("green").asCompact().icon("fa:circle-info"))
        .label(Label.of("Label 4").id("lbg-co-4").color("teal").asCompact().icon("fa:circle-info"))
        .label(Label.of("Label 5").id("lbg-co-5").color("purple").asCompact().icon("fa:circle-info"))
        .label(Label.of("Label 6").id("lbg-co-6").color("orange").asCompact().icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
