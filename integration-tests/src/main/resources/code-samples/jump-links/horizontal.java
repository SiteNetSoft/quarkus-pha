import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-horizontal")
        .item(JumpLinkItem.of("Top", "#top").asCurrent())
        .item(JumpLinkItem.of("Middle", "#middle"))
        .item(JumpLinkItem.of("Bottom", "#bottom"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
