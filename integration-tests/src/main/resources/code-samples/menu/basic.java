import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-basic").style("max-width: 220px")
        .item(MenuItem.of("Action 1"))
        .item(MenuItem.of("Action 2"))
        .item(MenuItem.of("Disabled action").asDisabled())
        .item(MenuItem.divider())
        .item(MenuItem.of("Delete").asDanger())
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
