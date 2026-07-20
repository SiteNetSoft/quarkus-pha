import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-loading").style("max-width: 220px")
        .item(MenuItem.of("Action"))
        .item(MenuItem.of("Link").href("#"))
        .item(MenuItem.of("Disabled action").asDisabled())
        .item(MenuItem.of("Disabled link").href("#").asDisabled())
        .item(MenuItem.divider())
        .loading("Loading items")
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
