import org.sitenetsoft.quarkus.pha.model.*;

Label label = Label
        .of("Label with set max-width truncation that keeps going well past the cap")
        .id("lbl-truncated").maxWidth("38ch").asRemovable();

LabelGroup group = LabelGroup.builder()
        .id("lbg-truncate").ariaLabel("Label group with truncation")
        .label(Label.of("Grouped label with a very long name").id("lbg-tr-1").color("blue").maxWidth("20ch"))
        .label(Label.of("Short label").id("lbg-tr-2").color("green"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label label=label /}
// {#include components/data-display/label-group group=group /}
