import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-subsections-inactive")
        .vertical().buttonLinks().ariaLabel("Vertical with inactive subsections")
        .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
        .item(JumpLinkItem.of("Purgatorio", "#")
                .subsAriaLabel("Purgatorio subsections")
                .sub(JumpLinkItem.of("Ante-Purgatory", "#"))
                .sub(JumpLinkItem.of("The Terraces", "#")))
        .item(JumpLinkItem.of("Paradiso", "#"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
