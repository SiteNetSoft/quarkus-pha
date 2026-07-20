package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Label;
import org.sitenetsoft.quarkus.pha.model.LabelGroup;

import java.util.List;

/**
 * Demo data for the label examples — every model-driven example on
 * /components/label is populated from these Label / LabelGroup models
 * (label-with-custom-render and editable-label-group-with-add-button stay
 * hand-written: rich HTML label bodies and the add-form composition are
 * outside the model's scope). Globals so the standalone example route
 * (which renders templates without data) can see them. Each is mirrored by
 * a snippet in resources/code-samples/label/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class LabelDemoData {

    public static List<Label> demoLabelsFilled = List.of(
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

    public static List<Label> demoLabelsOutlined = List.of(
            Label.of("Grey").id("lbl-outline-grey").asOutline(),
            Label.of("Blue").id("lbl-outline-blue").color("blue").asOutline(),
            Label.of("Teal").id("lbl-outline-teal").color("teal").asOutline(),
            Label.of("Green").id("lbl-outline-green").color("green").asOutline(),
            Label.of("Orange").id("lbl-outline-orange").color("orange").asOutline(),
            Label.of("Purple").id("lbl-outline-purple").color("purple").asOutline(),
            Label.of("Red").id("lbl-outline-red").color("red").asOutline(),
            Label.of("With icon").id("lbl-outline-icon").color("blue").asOutline().icon("fa:circle-info"),
            Label.of("Removable").id("lbl-outline-removable").color("blue").asOutline().asRemovable());

    public static List<Label> demoLabelsCompact = List.of(
            Label.of("Compact grey").id("lbl-cmp-grey").asCompact(),
            Label.of("Compact blue").id("lbl-cmp-blue").color("blue").asCompact(),
            Label.of("Compact outline").id("lbl-cmp-outline").asOutline().asCompact(),
            Label.of("Compact icon").id("lbl-cmp-icon").color("green").asCompact().icon("fa:circle-check"),
            Label.of("Compact removable").id("lbl-cmp-removable").color("blue").asCompact().asRemovable());

    public static Label demoLabelAdd = Label.of("Add Label").id("lbl-add").asAdd();

    public static Label demoLabelOverflow = Label.of("Overflow").id("lbl-overflow").asOverflow();

    public static LabelGroup demoLabelGroupBasic = LabelGroup.builder()
            .id("lbg-basic").ariaLabel("Basic label group")
            .label(Label.of("Label one").id("lbg-basic-lbl-1").color("blue"))
            .label(Label.of("Label two").id("lbg-basic-lbl-2").color("green"))
            .label(Label.of("Label three").id("lbg-basic-lbl-3").color("orange"))
            .build();

    public static LabelGroup demoLabelGroupPlatform = LabelGroup.builder()
            .id("lbg-cat-platform").categoryName("Platform")
            .label(Label.of("Kubernetes").id("lbg-cat-p-1").color("blue"))
            .label(Label.of("OpenShift").id("lbg-cat-p-2").color("red"))
            .label(Label.of("AWS").id("lbg-cat-p-3").color("orange"))
            .build();

    public static LabelGroup demoLabelGroupLanguage = LabelGroup.builder()
            .id("lbg-cat-language").categoryName("Language")
            .label(Label.of("Java").id("lbg-cat-l-1").color("orangered"))
            .label(Label.of("Go").id("lbg-cat-l-2").color("teal"))
            .label(Label.of("Python").id("lbg-cat-l-3").color("yellow"))
            .build();

    public static LabelGroup demoLabelGroupCompact = LabelGroup.builder()
            .id("lbg-compact").ariaLabel("Label group with compact labels")
            .label(Label.of("Label 1").id("lbg-c-1").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-c-2").color("blue").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-c-3").color("green").asCompact().icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupCompactOverflow = LabelGroup.builder()
            .id("lbg-compact-overflow").ariaLabel("Label group with compact labels and overflow").numLabels(3)
            .label(Label.of("Label 1").id("lbg-co-1").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-co-2").color("blue").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-co-3").color("green").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 4").id("lbg-co-4").color("teal").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 5").id("lbg-co-5").color("purple").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 6").id("lbg-co-6").color("orange").asCompact().icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupOverflow = LabelGroup.builder()
            .id("lbg-overflow").ariaLabel("Label group with overflow").numLabels(3)
            .label(Label.of("Label 1").id("lbg-of-1").icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-of-2").color("blue").icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-of-3").color("green").icon("fa:circle-info"))
            .label(Label.of("Label 4").id("lbg-of-4").color("orange").icon("fa:circle-info"))
            .label(Label.of("Label 5").id("lbg-of-5").color("red").icon("fa:circle-info"))
            .label(Label.of("Label 6").id("lbg-of-6").color("purple").icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupOverflowExpanded = LabelGroup.builder()
            .id("lbg-overflow-expanded").ariaLabel("Label group with overflow expanded")
            .numLabels(3).startExpanded()
            .label(Label.of("Label one").id("lbg-ofe-1").icon("fa:circle-info"))
            .label(Label.of("Label two").id("lbg-ofe-2").color("blue").icon("fa:circle-info"))
            .label(Label.of("Label three").id("lbg-ofe-3").color("green").icon("fa:circle-info"))
            .label(Label.of("Label four").id("lbg-ofe-4").color("teal").icon("fa:circle-info"))
            .label(Label.of("Label five").id("lbg-ofe-5").color("purple").icon("fa:circle-info"))
            .label(Label.of("Label six").id("lbg-ofe-6").color("orange").icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupRemovableCategory = LabelGroup.builder()
            .id("lbg-rm-cat").categoryName("Group label").closable("Remove category").numLabels(3)
            .label(Label.of("Label 1").id("lbg-rm-1").icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-rm-2").color("blue").icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-rm-3").color("green").icon("fa:circle-info"))
            .label(Label.of("Label 4").id("lbg-rm-4").color("orange").icon("fa:circle-info"))
            .label(Label.of("Label 5").id("lbg-rm-5").color("red").icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupVertical = LabelGroup.builder()
            .id("lbg-vertical").vertical().categoryName("Group label with a very long name")
            .closable("Remove category").numLabels(3)
            .label(Label.of("Label 1").id("lbg-v-1").icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-v-2").color("blue").icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-v-3").color("green").icon("fa:circle-info"))
            .label(Label.of("Label 4").id("lbg-v-4").color("orange").icon("fa:circle-info"))
            .label(Label.of("Label 5").id("lbg-v-5").color("red").icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupVerticalCategory = LabelGroup.builder()
            .id("lbg-vertical-category").vertical().categoryName("Category 1")
            .label(Label.of("Label 1").id("lbg-vcat-1").icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-vcat-2").color("blue").icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-vcat-3").color("green").icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupVerticalCompact = LabelGroup.builder()
            .id("lbg-vertical-compact").vertical().ariaLabel("Vertical label group with compact labels")
            .label(Label.of("Label 1").id("lbg-vc-1").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-vc-2").color("blue").asCompact().icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-vc-3").color("green").asCompact().icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupVerticalOverflow = LabelGroup.builder()
            .id("lbg-vertical-overflow").vertical().ariaLabel("Vertical label group with overflow").numLabels(3)
            .label(Label.of("Label 1").id("lbg-vof-1").icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-vof-2").color("blue").icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-vof-3").color("green").icon("fa:circle-info"))
            .label(Label.of("Label 4").id("lbg-vof-4").color("teal").icon("fa:circle-info"))
            .label(Label.of("Label 5").id("lbg-vof-5").color("purple").icon("fa:circle-info"))
            .label(Label.of("Label 6").id("lbg-vof-6").color("orange").icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupVerticalOverflowExpanded = LabelGroup.builder()
            .id("lbg-vertical-overflow-expanded").vertical()
            .ariaLabel("Vertical label group with overflow expanded").numLabels(3).startExpanded()
            .label(Label.of("Label 1").id("lbg-vofe-1").icon("fa:circle-info"))
            .label(Label.of("Label 2").id("lbg-vofe-2").color("blue").icon("fa:circle-info"))
            .label(Label.of("Label 3").id("lbg-vofe-3").color("green").icon("fa:circle-info"))
            .label(Label.of("Label 4").id("lbg-vofe-4").color("teal").icon("fa:circle-info"))
            .label(Label.of("Label 5").id("lbg-vofe-5").color("purple").icon("fa:circle-info"))
            .label(Label.of("Label 6").id("lbg-vofe-6").color("orange").icon("fa:circle-info"))
            .build();

    public static LabelGroup demoLabelGroupVerticalRemovableCategory = LabelGroup.builder()
            .id("lbg-vertical-category-removable").vertical().categoryName("Category 1")
            .closable("Close vertical label group")
            .label(Label.of("Label 1").id("lbg-vcatr-1").icon("fa:circle-info")
                    .removable("$el.closest('li').remove()"))
            .label(Label.of("Label 2").id("lbg-vcatr-2").color("blue").icon("fa:circle-info")
                    .removable("$el.closest('li').remove()"))
            .label(Label.of("Label 3").id("lbg-vcatr-3").color("green").icon("fa:circle-info")
                    .removable("$el.closest('li').remove()"))
            .build();

    public static LabelGroup demoLabelGroupStaticDynamic = LabelGroup.builder()
            .id("lbg-static-dynamic").ariaLabel("Static labels, dynamic label group")
            .label(Label.of("Static label 1").id("lbg-sd-1").color("blue").removable("$el.closest('li').remove()"))
            .label(Label.of("Static label 2").id("lbg-sd-2").color("blue").removable("$el.closest('li').remove()"))
            .label(Label.of("Static label 3").id("lbg-sd-3").color("blue").removable("$el.closest('li').remove()"))
            .build();

    public static LabelGroup demoLabelGroupMixed = LabelGroup.builder()
            .id("lbg-mixed").ariaLabel("Mixed labels, dynamic label group")
            .label(Label.of("Static label").id("lbg-mx-1").color("blue").removable("$el.closest('li').remove()"))
            .label(Label.of("Editable label").id("lbg-mx-2").color("blue").asEditable())
            .label(Label.of("Another static label").id("lbg-mx-3").color("blue")
                    .removable("$el.closest('li').remove()"))
            .build();

    public static Label demoLabelEditable = Label.of("Edit me").id("lbl-editable").color("blue").asEditable();

    public static LabelGroup demoLabelGroupEditable = LabelGroup.builder()
            .id("lbg-edit").categoryName("Tags")
            .label(Label.of("infrastructure").id("lbg-edit-1").color("blue").asEditable())
            .label(Label.of("monitoring").id("lbg-edit-2").color("blue").asEditable())
            .label(Label.of("backups").id("lbg-edit-3").color("blue").asEditable())
            .build();

    public static Label demoLabelTruncated = Label
            .of("Label with set max-width truncation that keeps going well past the cap")
            .id("lbl-truncated").maxWidth("38ch").asRemovable();

    public static LabelGroup demoLabelGroupTruncate = LabelGroup.builder()
            .id("lbg-truncate").ariaLabel("Label group with truncation")
            .label(Label.of("Grouped label with a very long name").id("lbg-tr-1").color("blue").maxWidth("20ch"))
            .label(Label.of("Short label").id("lbg-tr-2").color("green"))
            .build();
}
