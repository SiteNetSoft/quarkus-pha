import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Label> labels = List.of(
        Label.of("Compact grey").id("lbl-cmp-grey").asCompact(),
        Label.of("Compact blue").id("lbl-cmp-blue").color("blue").asCompact(),
        Label.of("Compact outline").id("lbl-cmp-outline").asOutline().asCompact(),
        Label.of("Compact icon").id("lbl-cmp-icon").color("green").asCompact().icon("fa:circle-check"),
        Label.of("Compact removable").id("lbl-cmp-removable").color("blue").asCompact().asRemovable());

// Template side, with the data in scope:
// {#for lbl in labels}{#include components/data-display/label label=lbl /}{/for}
