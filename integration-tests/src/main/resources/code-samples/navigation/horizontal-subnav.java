import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-horizontal-subnav")
        .ariaLabel("Local navigation")
        .subnav()
        .item("Overview", "#", true)
        .item("Details", "#")
        .item("YAML", "#")
        .item("Events", "#")
        .build();

// .subnav() renders pf-m-horizontal pf-m-subnav — local (tertiary) navigation
// under a page header. Template side:
// {#include components/navigation/nav nav=nav /}
