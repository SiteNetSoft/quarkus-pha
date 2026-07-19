import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-expandable")
        .ariaLabel("Expandable navigation")
        .expandable("Link 1", true,          // true = initially expanded
                Nav.item("Subnav link 1", "#", true),
                Nav.item("Subnav link 2", "#"),
                Nav.item("Subnav link 3", "#"))
        .expandable("Link 2",                // collapsed by default
                Nav.item("Subnav link 1", "#"),
                Nav.item("Subnav link 2", "#"))
        .build();

// Toggle buttons, aria wiring and the Alpine expand/collapse behaviour are all
// generated; toggle ids derive from the nav id. Template side:
// {#include components/navigation/nav nav=nav /}
