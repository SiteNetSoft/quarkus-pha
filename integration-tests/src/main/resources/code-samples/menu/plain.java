import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-plain").plain().style("max-width: 220px")
        .item(MenuItem.of("Action"))
        .item(MenuItem.of("Link").href("#"))
        .item(MenuItem.of("Disabled action").asDisabled())
        .item(MenuItem.of("Disabled link").href("#").asDisabled())
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
