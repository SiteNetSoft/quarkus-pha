import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-horizontal")
        .ariaLabel("Horizontal global navigation")
        .horizontal()
        .item("Item 1", "#", true)
        .item("Item 2", "#")
        .item("Item 3", "#")
        .item("Item 4", "#")
        .build();

// .horizontal() renders pf-m-horizontal — for the masthead content area.
// Template side:
// {#include components/navigation/nav nav=nav /}
