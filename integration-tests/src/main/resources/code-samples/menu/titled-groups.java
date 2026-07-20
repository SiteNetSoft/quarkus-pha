import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-titled-groups").style("max-width: 260px")
        .group(Menu.group(null,
                MenuItem.of("All resources")))
        .group(Menu.group("Group 1",
                MenuItem.of("Pods"),
                MenuItem.of("Deployments")))
        .group(Menu.group("Group 2",
                MenuItem.of("Services"),
                MenuItem.of("Routes")))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
