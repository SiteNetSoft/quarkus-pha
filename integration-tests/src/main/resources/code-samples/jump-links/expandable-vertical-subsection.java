import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-expandable")
        .vertical().expandable("Jump to section", true).style("max-width: 240px")
        .item(JumpLinkItem.of("Inferno", "#").asCurrent())
        .item(JumpLinkItem.of("Purgatorio", "#")
                .sub(JumpLinkItem.of("Ante-Purgatory", "#"))
                .sub(JumpLinkItem.of("The Terraces", "#")))
        .item(JumpLinkItem.of("Paradiso", "#"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
