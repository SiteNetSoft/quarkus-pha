import org.sitenetsoft.quarkus.pha.model.*;

// Dropdown is a composition: a menu-toggle plus a menu, wired together by the
// consumer. These builders produce the same anatomy this demo hand-rolls.
MenuToggle toggle = MenuToggle.of("Actions").id("dd-basic-toggle").build();

Menu menu = Menu.builder()
        .id("dd-basic-menu")
        .item(MenuItem.of("Edit"))
        .item(MenuItem.of("Duplicate"))
        .item(MenuItem.divider())
        .item(MenuItem.of("Delete").asDanger())
        .build();

// Template side — the wrapper owns the Alpine open state and anchors the menu:
// <div x-data="{ open: false }" style="position: relative; display: inline-block">
//   {#include components/navigation/menu-toggle toggleText='Actions' expandedExpr='open' /}
//   <div x-show="open" x-cloak style="position: absolute; top: 100%; z-index: 200"
//        @click.outside="open = false">
//     {#include components/navigation/menu menu=menu /}
//   </div>
// </div>
