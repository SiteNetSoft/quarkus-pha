import org.sitenetsoft.quarkus.pha.model.*;

Button button = Button.of("Block level button spans its container")
        .id("btn-block").variant("primary").asBlock().build();

// Template side, with the data in scope:
// {#include components/actions/button btn=button /}
