import org.sitenetsoft.quarkus.pha.model.*;

// Kebab dropdown: a plain icon-only toggle plus a small action menu.
Menu menu = Menu.builder()
        .id("dd-kebab-menu")
        .item(MenuItem.of("Refresh"))
        .item(MenuItem.of("Export"))
        .build();

// Template side — the plain kebab toggle is the param-mode menu-toggle include:
// <div x-data="{ open: false }" style="position: relative; display: inline-block">
//   {#include components/navigation/menu-toggle plain=true toggleIcon='fa:ellipsis-vertical'
//       ariaLabel='More actions' expandedExpr='open' /}
//   <div x-show="open" x-cloak style="position: absolute; top: 100%; z-index: 200"
//        @click.outside="open = false">
//     {#include components/navigation/menu menu=menu /}
//   </div>
// </div>
