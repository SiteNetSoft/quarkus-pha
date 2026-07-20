import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Label> labels = List.of(
        Label.of("Grey").id("lbl-grey"),
        Label.of("Blue").id("lbl-blue").color("blue"),
        Label.of("Teal").id("lbl-teal").color("teal"),
        Label.of("Green").id("lbl-green").color("green"),
        Label.of("Orange").id("lbl-orange").color("orange"),
        Label.of("Orangered").id("lbl-orangered").color("orangered"),
        Label.of("Purple").id("lbl-purple").color("purple"),
        Label.of("Red").id("lbl-red").color("red"),
        Label.of("Yellow").id("lbl-yellow").color("yellow"),
        Label.of("Removable").id("lbl-removable").color("blue").asRemovable(),
        Label.of("Info").id("lbl-info").status("info"),
        Label.of("Success").id("lbl-success").status("success"),
        Label.of("Warning").id("lbl-warning").status("warning"),
        Label.of("Danger").id("lbl-danger").status("danger"),
        Label.of("Disabled").id("lbl-disabled").asDisabled(),
        Label.of("Clickable").id("lbl-clickable").color("blue").asClickable().href("#filled-labels"));

// Template side, with the data in scope:
// {#for lbl in labels}{#include components/data-display/label label=lbl /}{/for}
