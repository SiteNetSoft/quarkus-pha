import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-expandable-responsive-no-label")
        .vertical().ariaLabel("Expandable responsive with no label")
        .expandableResponsive("pf-m-non-expandable-on-md pf-m-expandable-on-lg pf-m-non-expandable-on-xl",
                "Jump to section")
        .style("max-width: 240px")
        .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
        .item(JumpLinkItem.of("Purgatorio", "#"))
        .item(JumpLinkItem.of("Paradiso", "#"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
