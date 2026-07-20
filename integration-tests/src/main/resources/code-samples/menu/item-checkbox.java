import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-item-checkbox").style("max-width: 260px")
        .checkboxes("mn-check")
        .item(MenuItem.of("Checkbox 1"))
        .item(MenuItem.of("Checkbox 2").asSelected())
        .item(MenuItem.of("Checkbox 3"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
