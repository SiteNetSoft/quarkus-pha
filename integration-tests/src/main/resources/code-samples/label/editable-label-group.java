import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup group = LabelGroup.builder()
        .id("lbg-edit").categoryName("Tags")
        .label(Label.of("infrastructure").id("lbg-edit-1").color("blue").asEditable())
        .label(Label.of("monitoring").id("lbg-edit-2").color("blue").asEditable())
        .label(Label.of("backups").id("lbg-edit-3").color("blue").asEditable())
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=group /}
