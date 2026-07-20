import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-scrollable").scrollable().style("max-width: 220px")
        .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                MenuItem.of("Action 4"), MenuItem.of("Action 5"), MenuItem.of("Action 6"),
                MenuItem.of("Action 7"), MenuItem.of("Action 8"), MenuItem.of("Action 9"),
                MenuItem.of("Action 10"), MenuItem.of("Action 11"), MenuItem.of("Action 12"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
