import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-icons")
        .ariaLabel("Navigation with icons")
        .icon("Dashboard", "#", "fa:tachometer-alt", true)
        .icon("Servers", "#", "fa:server")
        .icon("Databases", "#", "fa:database")
        .icon("Settings", "#", "fa:gear")
        .build();

// The icon name is "set:slug" for the vendored icon sets (fa, fa-regular,
// fa-brands, pficon); the SVG is inlined server-side. Template side:
// {#include components/navigation/nav nav=nav /}
