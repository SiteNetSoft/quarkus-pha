import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-view-more").style("max-width: 260px")
        .viewMore("View more", 3, "Loaded action ")
        .item(MenuItem.of("Action 1"))
        .item(MenuItem.of("Action 2"))
        .item(MenuItem.of("Action 3"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
