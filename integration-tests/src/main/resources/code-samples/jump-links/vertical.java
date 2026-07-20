import org.sitenetsoft.quarkus.pha.model.*;

JumpLinks jumpLinks = JumpLinks.of("jl-vertical").vertical()
        .item(JumpLinkItem.of("Examples", "#examples").asCurrent())
        .item(JumpLinkItem.of("Documentation", "#documentation"))
        .item(JumpLinkItem.of("Usage", "#usage"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/jump-links jumpLinks=jumpLinks /}
