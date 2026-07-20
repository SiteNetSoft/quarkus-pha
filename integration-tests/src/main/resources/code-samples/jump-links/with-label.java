import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks plain = JumpLinks.of("jl-with-label")
        .label("Jump to section")
        .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
        .item(JumpLinkItem.of("Purgatorio", "#"))
        .item(JumpLinkItem.of("Paradiso", "#"))
        .build();

JumpLinks centered = JumpLinks.of("jl-with-label-center")
        .center().label("Jump to section").ariaLabel("Jump to section centered")
        .item(JumpLinkItem.of("Inferno", "#").asCurrentLocation())
        .item(JumpLinkItem.of("Purgatorio", "#"))
        .item(JumpLinkItem.of("Paradiso", "#"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=plain /}
// {#include components/navigation/jump-links jumpLinks=centered /}
