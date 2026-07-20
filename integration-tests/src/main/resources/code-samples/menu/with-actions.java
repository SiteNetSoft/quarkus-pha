import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-actions").plain().style("max-width: 260px")
        .item(MenuItem.of("Item 1").asFavoriteAction())
        .item(MenuItem.of("Item 2").action("fa:clipboard", "Copy"))
        .item(MenuItem.of("Item 3").action("fa:bell", "Notifications"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
