import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-vertical-compact").vertical().ariaLabel("Vertical label group with compact labels")
        .label(Label.of("Label 1").id("lbg-vc-1").asCompact().icon("fa:circle-info"))
        .label(Label.of("Label 2").id("lbg-vc-2").color("blue").asCompact().icon("fa:circle-info"))
        .label(Label.of("Label 3").id("lbg-vc-3").color("green").asCompact().icon("fa:circle-info"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
