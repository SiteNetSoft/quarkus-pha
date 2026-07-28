import org.sitenetsoft.quarkus.pha.model.*;

// Multi-select menu whose items carry action buttons — action(icon, ariaLabel)
// renders the trailing pf-v6-c-menu__item-action.
MenuToggle toggle = MenuToggle.of("Actions").build();

Menu menu = Menu.builder()
        .id("cm-actions-menu")
        .selectMulti()
        .group(Menu.group("Actions",
                MenuItem.of("Item 1").asSelected().action("fa:code-branch", "Code"),
                MenuItem.of("Item 2").asDisabled().action("fa:bell", "Alert"),
                MenuItem.of("Item 3").action("fa:clipboard", "Copy"),
                MenuItem.of("Item 4").action("fa:bars", "Expand")))
        .build();

// Template side — the composition wrapper owns the Alpine open state and the
// client-side multi-select tracking:
// {#include components/navigation/menu-toggle toggleText='Actions' expandedExpr='open' /}
// ... {#include components/navigation/menu menu=menu /}
