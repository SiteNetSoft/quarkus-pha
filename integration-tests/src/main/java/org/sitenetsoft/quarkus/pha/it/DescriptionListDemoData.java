package org.sitenetsoft.quarkus.pha.it;

import java.util.List;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.DescriptionList;
import org.sitenetsoft.quarkus.pha.model.DescriptionListGroup;

/**
 * Demo data for the description-list examples — every model-driven example on
 * /components/description-list is populated from one of these DescriptionList
 * models (term-help-text stays hand-written: its help terms open a Popover
 * composition). Globals so the standalone example route (which renders
 * templates without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/description-list/ served on the example card's Java
 * tab — keep them in sync when editing.
 */
@TemplateGlobal
public class DescriptionListDemoData {

    /** PF's canonical demo content, shared by all layout-modifier examples. */
    private static List<DescriptionListGroup> standardGroups() {
        return List.of(
                DescriptionListGroup.of("Name", "Example"),
                DescriptionListGroup.link("Namespace", "mary-test"),
                DescriptionListGroup.of("Labels", "example"),
                DescriptionListGroup.linkButton("Pod selector", "app=MyApp", "fa:circle-plus"),
                DescriptionListGroup.of("Annotation", "2 Annotations"));
    }

    public static DescriptionList demoDescBasic = DescriptionList.builder()
            .id("dl-basic")
            .group("Name", "Jane Doe")
            .group("Email", "jane@example.com")
            .group("Role", "Senior engineer")
            .build();

    public static DescriptionList demoDescFillColumns = DescriptionList.builder()
            .id("dl-fill").fillColumns()
            .group("Status", "Active")
            .group("Created", "2026-05-20")
            .build();

    public static DescriptionList demoDescCompact = DescriptionList.builder()
            .id("dl-compact").ariaLabel("Description list compact")
            .compact().groups(standardGroups())
            .build();

    public static DescriptionList demoDescCompactHorizontal = DescriptionList.builder()
            .id("dl-compact-horizontal").ariaLabel("Description list compact-horizontal")
            .compact().horizontal().groups(standardGroups())
            .build();

    public static DescriptionList demoDescHorizontal = DescriptionList.builder()
            .id("dl-horizontal").ariaLabel("Description list horizontal")
            .horizontal().groups(standardGroups())
            .build();

    public static DescriptionList demoDescFluidHorizontal = DescriptionList.builder()
            .id("dl-fluid-horizontal").ariaLabel("Description list fluid-horizontal")
            .horizontal().fluid().groups(standardGroups())
            .build();

    public static DescriptionList demoDescDefault2Col = DescriptionList.builder()
            .id("dl-default-2-col").ariaLabel("Description list default-2-col")
            .columns("2-col").groups(standardGroups())
            .build();

    public static DescriptionList demoDescDefault3ColOnLg = DescriptionList.builder()
            .id("dl-default-3-col-on-lg").ariaLabel("Description list default-3-col-on-lg")
            .columns("3-col-on-lg").groups(standardGroups())
            .build();

    public static DescriptionList demoDescDefaultAutoColumnWidth = DescriptionList.builder()
            .id("dl-default-auto-column-width").ariaLabel("Description list default-auto-column-width")
            .autoColumnWidths().columns("3-col").groups(standardGroups())
            .build();

    public static DescriptionList demoDescDefaultInlineGrid = DescriptionList.builder()
            .id("dl-default-inline-grid").ariaLabel("Description list default-inline-grid")
            .inlineGrid().groups(standardGroups())
            .build();

    public static DescriptionList demoDescDefaultResponsiveColumns = DescriptionList.builder()
            .id("dl-default-responsive-columns").ariaLabel("Description list default-responsive-columns")
            .columns("2-col-on-lg", "3-col-on-xl").groups(standardGroups())
            .build();

    public static DescriptionList demoDescHorizontal2Col = DescriptionList.builder()
            .id("dl-horizontal-2-col").ariaLabel("Description list horizontal-2-col")
            .horizontal().columns("2-col").groups(standardGroups())
            .build();

    public static DescriptionList demoDescHorizontal3ColOnLg = DescriptionList.builder()
            .id("dl-horizontal-3-col-on-lg").ariaLabel("Description list horizontal-3-col-on-lg")
            .horizontal().columns("3-col-on-lg").groups(standardGroups())
            .build();

    public static DescriptionList demoDescHorizontalAutoColumnWidth = DescriptionList.builder()
            .id("dl-horizontal-auto-column-width").ariaLabel("Description list horizontal-auto-column-width")
            .horizontal().autoColumnWidths().columns("2-col").groups(standardGroups())
            .build();

    public static DescriptionList demoDescHorizontalResponsiveColumns = DescriptionList.builder()
            .id("dl-horizontal-responsive-columns").ariaLabel("Description list horizontal-responsive-columns")
            .horizontal().columns("2-col-on-lg", "3-col-on-xl").groups(standardGroups())
            .build();

    public static DescriptionList demoDescHorizontalCustomTermWidth = DescriptionList.builder()
            .id("dl-horizontal-custom-term-width").ariaLabel("Description list horizontal-custom-term-width")
            .horizontal()
            .styleVars("--pf-v6-c-description-list--m-horizontal__term--width: 12ch; "
                    + "--pf-v6-c-description-list--m-horizontal__term--width-on-md: 20ch; "
                    + "--pf-v6-c-description-list--m-horizontal__term--width-on-lg: 28ch")
            .groups(standardGroups())
            .build();

    public static DescriptionList demoDescResponsiveHoriVertGroup = DescriptionList.builder()
            .id("dl-responsive-hori-vert-group").ariaLabel("Description list responsive-hori-vert-group")
            .horizontal()
            .orientations("vertical-on-md", "horizontal-on-lg", "vertical-on-xl", "horizontal-on-2xl")
            .groups(standardGroups())
            .build();

    public static DescriptionList demoDescAutoFit = DescriptionList.builder()
            .id("dl-auto-fit").ariaLabel("Description list auto-fit")
            .autoFit().groups(standardGroups())
            .build();

    public static DescriptionList demoDescAutoFitMinWidth = DescriptionList.builder()
            .id("dl-auto-fit-min-width").ariaLabel("Description list auto-fit-min-width")
            .autoFit()
            .styleVars("--pf-v6-c-description-list--GridTemplateColumns--min: 200px")
            .groups(standardGroups())
            .build();

    public static DescriptionList demoDescAutoFitMinWidthResponsive = DescriptionList.builder()
            .id("dl-auto-fit-min-width-responsive").ariaLabel("Description list auto-fit-min-width-responsive")
            .autoFit()
            .styleVars("--pf-v6-c-description-list--GridTemplateColumns--min-on-md: 100px; "
                    + "--pf-v6-c-description-list--GridTemplateColumns--min-on-lg: 150px; "
                    + "--pf-v6-c-description-list--GridTemplateColumns--min-on-xl: 200px; "
                    + "--pf-v6-c-description-list--GridTemplateColumns--min-on-2xl: 300px")
            .groups(standardGroups())
            .build();

    public static DescriptionList demoDescLargeDisplaySize = DescriptionList.builder()
            .id("dl-large-display-size").ariaLabel("Description list large-display-size")
            .displayLg().groups(standardGroups())
            .build();

    public static DescriptionList demoDescDisplay2xl = DescriptionList.builder()
            .id("dl-display-2xl").ariaLabel("Description list 2xl display size")
            .display2xl().groups(standardGroups())
            .build();

    public static DescriptionList demoDescLargeDisplaySizeAndCard = DescriptionList.builder()
            .id("dl-large-display-size-and-card").ariaLabel("Description list large-display-size-and-card")
            .displayLg().columns("2-col-on-lg").cardGroups().groups(standardGroups())
            .build();

    public static DescriptionList demoDescWithCard = DescriptionList.builder()
            .id("dl-with-card").ariaLabel("Description list with-card")
            .columns("2-col-on-lg").cardGroups().groups(standardGroups())
            .build();

    public static DescriptionList demoDescDisplaySizeCard3ColLg = DescriptionList.builder()
            .id("dl-display-size-card-3-col-lg").ariaLabel("Description list display-size-card-3-col-lg")
            .displayLg().columns("3-col-on-lg").cardGroups().groups(standardGroups())
            .build();

    public static DescriptionList demoDescDisplaySizeCardHorizontalTermWidth = DescriptionList.builder()
            .id("dl-display-size-card-horizontal-term-width")
            .ariaLabel("Description list display-size-card-horizontal-term-width")
            .displayLg().horizontal().columns("2-col-on-lg").cardGroups()
            .styleVars("--pf-v6-c-description-list__term--width: 10ch")
            .groups(standardGroups())
            .build();

    public static DescriptionList demoDescIconsOnTerms = DescriptionList.builder()
            .id("dl-icons-on-terms").ariaLabel("Description list icons-on-terms")
            .group(DescriptionListGroup.of("Name", "Example").termIcon("fa:cube"))
            .group(DescriptionListGroup.link("Namespace", "mary-test").termIcon("fa:book"))
            .group(DescriptionListGroup.of("Labels", "example").termIcon("fa:key"))
            .group(DescriptionListGroup.linkButton("Pod selector", "app=MyApp", "fa:circle-plus").termIcon("fa:globe"))
            .group(DescriptionListGroup.of("Annotation", "2 Annotations").termIcon("fa:flag"))
            .build();
}
