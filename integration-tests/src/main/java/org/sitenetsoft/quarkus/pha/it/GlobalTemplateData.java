package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Nav;

@TemplateGlobal
public class GlobalTemplateData {
    public static String applicationName = "quarkus-pha";

    /*
     * Demo data for the navigation examples — every example on /components/navigation
     * is populated from one of these Nav models. Globals so the standalone example
     * route (which renders templates without data) can see them. Each nav is mirrored
     * by a snippet in resources/code-samples/navigation/{slug}.java served on the
     * example card's Java tab — keep them in sync when editing.
     */

    /** Vertical: flat list of links. */
    public static Nav demoVerticalNav = Nav.builder()
            .id("nav-vertical")
            .ariaLabel("Global navigation")
            .item("Dashboard", "#", true)
            .item("Projects", "#")
            .item("Members", "#")
            .item("Billing", "#")
            .item("Settings", "#")
            .build();

    /** Grouped: titled sections only. */
    public static Nav demoGroupedNav = Nav.builder()
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

    /** Expandable: items with subnavs, per-item initial state. */
    public static Nav demoExpandableNav = Nav.builder()
            .id("nav-expandable")
            .ariaLabel("Expandable navigation")
            .expandable("Link 1", true,
                    Nav.item("Subnav link 1", "#", true),
                    Nav.item("Subnav link 2", "#"),
                    Nav.item("Subnav link 3", "#"))
            .expandable("Link 2",
                    Nav.item("Subnav link 1", "#"),
                    Nav.item("Subnav link 2", "#"))
            .build();

    /** Third level: an expandable nested inside another expandable's subnav. */
    public static Nav demoThirdLevelNav = Nav.builder()
            .id("nav-expandable-third-level")
            .ariaLabel("Multi-level navigation")
            .expandable("Link 1", true,
                    Nav.item("Second level link 1", "#", true),
                    Nav.expandable("Second level link 2",
                            Nav.item("Third level link 1", "#"),
                            Nav.item("Third level link 2", "#")))
            .build();

    /** Mixed: plain links and expandable items sharing one list. */
    public static Nav demoMixedNav = Nav.builder()
            .id("nav-mixed")
            .ariaLabel("Mixed navigation")
            .item("Link 1 (not expandable)", "#", true)
            .expandable("Link 2 (expandable)",
                    Nav.item("Subnav link 1", "#"),
                    Nav.item("Subnav link 2", "#"))
            .item("Link 3 (not expandable)", "#")
            .build();

    /** Horizontal: masthead content-area navigation. */
    public static Nav demoHorizontalNav = Nav.builder()
            .id("nav-horizontal")
            .ariaLabel("Horizontal global navigation")
            .horizontal()
            .item("Item 1", "#", true)
            .item("Item 2", "#")
            .item("Item 3", "#")
            .item("Item 4", "#")
            .build();

    /** Horizontal subnav: local (tertiary) navigation under a page header. */
    public static Nav demoSubnavNav = Nav.builder()
            .id("nav-horizontal-subnav")
            .ariaLabel("Local navigation")
            .subnav()
            .item("Overview", "#", true)
            .item("Details", "#")
            .item("YAML", "#")
            .item("Events", "#")
            .build();

    /** With item icons: each link carries a leading icon. */
    public static Nav demoIconsNav = Nav.builder()
            .id("nav-icons")
            .ariaLabel("Navigation with icons")
            .icon("Dashboard", "#", "fa:tachometer-alt", true)
            .icon("Servers", "#", "fa:server")
            .icon("Databases", "#", "fa:database")
            .icon("Settings", "#", "fa:gear")
            .build();
}
