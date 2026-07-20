import org.sitenetsoft.quarkus.pha.model.*;

LabelGroup platform = LabelGroup.builder()
        .id("lbg-cat-platform").categoryName("Platform")
        .label(Label.of("Kubernetes").id("lbg-cat-p-1").color("blue"))
        .label(Label.of("OpenShift").id("lbg-cat-p-2").color("red"))
        .label(Label.of("AWS").id("lbg-cat-p-3").color("orange"))
        .build();

LabelGroup language = LabelGroup.builder()
        .id("lbg-cat-language").categoryName("Language")
        .label(Label.of("Java").id("lbg-cat-l-1").color("orangered"))
        .label(Label.of("Go").id("lbg-cat-l-2").color("teal"))
        .label(Label.of("Python").id("lbg-cat-l-3").color("yellow"))
        .build();

// Template side, with the data in scope:
// {#include components/data-display/label-group group=platform /}
// {#include components/data-display/label-group group=language /}
