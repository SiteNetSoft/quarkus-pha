import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-footer").style("max-width: 220px")
        .item(MenuItem.of("Action"))
        .item(MenuItem.of("Link").href("#"))
        .footerButton("Footer action")
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
