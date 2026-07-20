import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-vertical-label")
        .vertical().label("Jump to section").style("max-width: 200px")
        .item(JumpLinkItem.of("Inferno", "#").asCurrent())
        .item(JumpLinkItem.of("Purgatorio", "#"))
        .item(JumpLinkItem.of("Paradiso", "#"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
