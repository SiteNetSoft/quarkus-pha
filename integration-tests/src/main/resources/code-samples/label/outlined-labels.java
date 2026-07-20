import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Label> labels = List.of(
        Label.of("Grey").id("lbl-outline-grey").asOutline(),
        Label.of("Blue").id("lbl-outline-blue").color("blue").asOutline(),
        Label.of("Teal").id("lbl-outline-teal").color("teal").asOutline(),
        Label.of("Green").id("lbl-outline-green").color("green").asOutline(),
        Label.of("Orange").id("lbl-outline-orange").color("orange").asOutline(),
        Label.of("Purple").id("lbl-outline-purple").color("purple").asOutline(),
        Label.of("Red").id("lbl-outline-red").color("red").asOutline(),
        Label.of("With icon").id("lbl-outline-icon").color("blue").asOutline().icon("fa:circle-info"),
        Label.of("Removable").id("lbl-outline-removable").color("blue").asOutline().asRemovable());

// Template side, with the data in scope:
// {#for lbl in labels}{#include components/data-display/label label=lbl /}{/for}
