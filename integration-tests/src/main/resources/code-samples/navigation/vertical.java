import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-vertical")
        .ariaLabel("Global navigation")
        .item("Dashboard", "#", true)
        .item("Projects", "#")
        .item("Members", "#")
        .item("Billing", "#")
        .item("Settings", "#")
        .build();

// Template side, with `nav` in the template data:
// {#include components/navigation/nav nav=nav /}
