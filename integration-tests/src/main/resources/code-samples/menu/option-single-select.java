import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-option-single-select").style("max-width: 260px")
        .selectSingle()
        .item(MenuItem.of("Option 1").asSelected())
        .item(MenuItem.of("Option 2"))
        .item(MenuItem.of("Option 3"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
