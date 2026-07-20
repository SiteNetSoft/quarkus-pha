import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-centered").center().ariaLabel("Sections")
        .item(JumpLinkItem.of("One", "#a").asCurrent())
        .item(JumpLinkItem.of("Two", "#b"))
        .item(JumpLinkItem.of("Three", "#c"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
