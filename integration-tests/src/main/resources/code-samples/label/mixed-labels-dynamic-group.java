import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-mixed").ariaLabel("Mixed labels, dynamic label group")
        .label(Label.of("Static label").id("lbg-mx-1").color("blue").removable("$el.closest('li').remove()"))
        .label(Label.of("Editable label").id("lbg-mx-2").color("blue").asEditable())
        .label(Label.of("Another static label").id("lbg-mx-3").color("blue")
                .removable("$el.closest('li').remove()"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
