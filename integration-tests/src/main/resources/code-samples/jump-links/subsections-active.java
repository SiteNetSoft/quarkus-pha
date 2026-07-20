import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-subsections-active")
        .vertical().buttonLinks().ariaLabel("Vertical with active subsections")
        .item(JumpLinkItem.of("Inferno", "#"))
        .item(JumpLinkItem.of("Purgatorio", "#")
                .subsAriaLabel("Purgatorio subsections")
                .sub(JumpLinkItem.of("Ante-Purgatory", "#").asCurrentLocation())
                .sub(JumpLinkItem.of("The Terraces", "#")))
        .item(JumpLinkItem.of("Paradiso", "#"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
