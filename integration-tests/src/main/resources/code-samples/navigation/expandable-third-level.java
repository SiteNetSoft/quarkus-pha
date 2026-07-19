import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-expandable-third-level")
        .ariaLabel("Multi-level navigation")
        .expandable("Link 1", true,
                Nav.item("Second level link 1", "#", true),
                Nav.expandable("Second level link 2",
                        Nav.item("Third level link 1", "#"),
                        Nav.item("Third level link 2", "#")))
        .build();

// Nav.expandable(...) nests inside another expandable's children; the runtime
// template recurses, so nesting depth is unlimited. Template side:
// {#include components/navigation/nav nav=nav /}
