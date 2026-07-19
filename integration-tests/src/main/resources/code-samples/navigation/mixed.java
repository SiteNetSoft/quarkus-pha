import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-mixed")
        .ariaLabel("Mixed navigation")
        .item("Link 1 (not expandable)", "#", true)
        .expandable("Link 2 (expandable)",
                Nav.item("Subnav link 1", "#"),
                Nav.item("Subnav link 2", "#"))
        .item("Link 3 (not expandable)", "#")
        .build();

// Plain items and expandables mix freely in a flat list (only groups cannot
// be mixed with loose items). Template side:
// {#include components/navigation/nav nav=nav /}
