import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-basic").ariaLabel("Basic label group")
        .label(Label.of("Label one").id("lbg-basic-lbl-1").color("blue"))
        .label(Label.of("Label two").id("lbg-basic-lbl-2").color("green"))
        .label(Label.of("Label three").id("lbg-basic-lbl-3").color("orange"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
