import org.sitenetsoft.quarkus.pha.model.Nav;

Nav nav = Nav.builder()
        .id("nav-grouped")
        .ariaLabel("Grouped navigation")
        .group("Workspace",
                Nav.item("Inbox", "#", true),
                Nav.item("Drafts", "#"))
        .group("Account",
                Nav.item("Profile", "#"),
                Nav.item("Security", "#"),
                Nav.item("Notifications", "#"))
        .build();

// A grouped nav is sections-only: mixing .group(...) with loose .item(...)
// calls throws at build(). Template side:
// {#include components/navigation/nav nav=nav /}
