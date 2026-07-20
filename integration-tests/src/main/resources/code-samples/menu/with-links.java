import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-links").style("max-width: 220px")
        .item(MenuItem.of("Link 1").href("#").asExternal())
        .item(MenuItem.of("Link 2").href("#").asExternal())
        .item(MenuItem.of("Link 3").href("#"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
