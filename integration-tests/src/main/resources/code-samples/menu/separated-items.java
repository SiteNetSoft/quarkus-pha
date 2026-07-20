import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-separated").style("max-width: 220px")
        .item(MenuItem.of("Action 1"))
        .item(MenuItem.divider())
        .item(MenuItem.of("Action 2"))
        .item(MenuItem.divider())
        .item(MenuItem.of("Action 3"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
