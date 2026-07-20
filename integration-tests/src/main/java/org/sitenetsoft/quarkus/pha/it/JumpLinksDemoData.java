package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.JumpLinkItem;
import org.sitenetsoft.quarkus.pha.model.JumpLinks;

import java.util.List;

/**
 * Demo data for the jump-links examples — every example on
 * /components/jump-links is populated from these JumpLinks models. Globals so
 * the standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/jump-links/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class JumpLinksDemoData {

    public static JumpLinks demoJlHorizontal = JumpLinks.of("jl-horizontal")
            .item(JumpLinkItem.of("Top", "#top").asCurrent())
            .item(JumpLinkItem.of("Middle", "#middle"))
            .item(JumpLinkItem.of("Bottom", "#bottom"))
            .build();

    public static JumpLinks demoJlVertical = JumpLinks.of("jl-vertical").vertical()
            .item(JumpLinkItem.of("Examples", "#examples").asCurrent())
            .item(JumpLinkItem.of("Documentation", "#documentation"))
            .item(JumpLinkItem.of("Usage", "#usage"))
            .build();

    public static JumpLinks demoJlCentered = JumpLinks.of("jl-centered").center().ariaLabel("Sections")
            .item(JumpLinkItem.of("One", "#a").asCurrent())
            .item(JumpLinkItem.of("Two", "#b"))
            .item(JumpLinkItem.of("Three", "#c"))
            .build();

    public static JumpLinks demoJlWithLabel = JumpLinks.of("jl-with-label")
            .label("Jump to section")
            .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
            .item(JumpLinkItem.of("Purgatorio", "#"))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();

    public static JumpLinks demoJlWithLabelCentered = JumpLinks.of("jl-with-label-center")
            .center().label("Jump to section").ariaLabel("Jump to section centered")
            .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
            .item(JumpLinkItem.of("Purgatorio", "#"))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();

    public static JumpLinks demoJlVerticalLabel = JumpLinks.of("jl-vertical-label")
            .vertical().label("Jump to section").style("max-width: 200px")
            .item(JumpLinkItem.of("Inferno", "#").asCurrent())
            .item(JumpLinkItem.of("Purgatorio", "#"))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();

    public static JumpLinks demoJlSubsectionsActive = JumpLinks.of("jl-subsections-active")
            .vertical().buttonLinks().ariaLabel("Vertical with active subsections")
            .item(JumpLinkItem.of("Inferno", "#"))
            .item(JumpLinkItem.of("Purgatorio", "#")
                    .subsAriaLabel("Purgatorio subsections")
                    .sub(JumpLinkItem.of("Ante-Purgatory", "#").asCurrentLocation())
                    .sub(JumpLinkItem.of("The Terraces", "#")))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();

    public static JumpLinks demoJlSubsectionsInactive = JumpLinks.of("jl-subsections-inactive")
            .vertical().buttonLinks().ariaLabel("Vertical with inactive subsections")
            .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
            .item(JumpLinkItem.of("Purgatorio", "#")
                    .subsAriaLabel("Purgatorio subsections")
                    .sub(JumpLinkItem.of("Ante-Purgatory", "#"))
                    .sub(JumpLinkItem.of("The Terraces", "#")))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();

    public static JumpLinks demoJlExpandableResponsive = JumpLinks.of("jl-expandable-responsive")
            .vertical().ariaLabel("Expandable responsive").label("Jump to section")
            .expandableResponsive("pf-m-non-expandable-on-md pf-m-expandable-on-lg pf-m-non-expandable-on-xl",
                    "Jump to section")
            .style("max-width: 240px")
            .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
            .item(JumpLinkItem.of("Purgatorio", "#"))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();

    public static JumpLinks demoJlExpandableResponsiveNoLabel = JumpLinks.of("jl-expandable-responsive-no-label")
            .vertical().ariaLabel("Expandable responsive with no label")
            .expandableResponsive("pf-m-non-expandable-on-md pf-m-expandable-on-lg pf-m-non-expandable-on-xl",
                    "Jump to section")
            .style("max-width: 240px")
            .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
            .item(JumpLinkItem.of("Purgatorio", "#"))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();

    public static JumpLinks demoJlExpandableSubsection = JumpLinks.of("jl-expandable")
            .vertical().expandable("Jump to section", true).style("max-width: 240px")
            .item(JumpLinkItem.of("Inferno", "#").asCurrent())
            .item(JumpLinkItem.of("Purgatorio", "#")
                    .sub(JumpLinkItem.of("Ante-Purgatory", "#"))
                    .sub(JumpLinkItem.of("The Terraces", "#")))
            .item(JumpLinkItem.of("Paradiso", "#"))
            .build();
}
