import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-static-dynamic").ariaLabel("Static labels, dynamic label group")
        .label(Label.of("Static label 1").id("lbg-sd-1").color("blue").removable("$el.closest('li').remove()"))
        .label(Label.of("Static label 2").id("lbg-sd-2").color("blue").removable("$el.closest('li').remove()"))
        .label(Label.of("Static label 3").id("lbg-sd-3").color("blue").removable("$el.closest('li').remove()"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
