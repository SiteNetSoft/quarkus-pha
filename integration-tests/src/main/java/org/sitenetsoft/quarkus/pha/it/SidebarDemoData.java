package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Sidebar;

/**
 * Demo data for the sidebar examples — all examples on /components/sidebar are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/sidebar/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class SidebarDemoData {

    private static final String SECONDARY_BG =
            "background: var(--pf-t--global--background--color--secondary--default); padding: 1rem";

    private static final String SCROLL_ROWS = """
            <p>Scrollable content row 1</p>
            <p>Scrollable content row 2</p>
            <p>Scrollable content row 3</p>
            <p>Scrollable content row 4</p>
            <p>Scrollable content row 5</p>
            <p>Scrollable content row 6</p>
            <p>Scrollable content row 7</p>
            <p>Scrollable content row 8</p>
            <p>Scrollable content row 9</p>
            <p>Scrollable content row 10</p>
            <p>Scrollable content row 11</p>
            <p>Scrollable content row 12</p>""";

    public static Sidebar demoSbBasic = Sidebar.of("sb-basic")
            .panelStyle(SECONDARY_BG)
            .panel("Panel — filters, secondary nav, or context info.")
            .contentStyle("padding: 1rem")
            .content("Main content sits next to the panel. The sidebar component itself doesn't impose"
                    + " a width; control via CSS variables or container size.")
            .build();

    public static Sidebar demoSbBorder = Sidebar.of("sb-border").split()
            .panelSecondary().panelPadding()
            .panel("Split border between panel and content (pf-m-split).")
            .contentPadding().content("A divider line separates the two regions.")
            .build();

    public static Sidebar demoSbPaddingContent = Sidebar.of("sb-padding-content")
            .panelSecondary().panel("Panel without padding.")
            .contentPadding().content("Only the content opts into padding (pf-m-padding).")
            .build();

    public static Sidebar demoSbPaddingPanel = Sidebar.of("sb-padding-panel")
            .panelSecondary().panelPadding()
            .panel("Only the panel opts into padding (pf-m-padding).")
            .content("Content without the padding modifier.")
            .build();

    public static Sidebar demoSbPanelRightGutter = Sidebar.of("sb-panel-right-gutter")
            .panelRight().gutter().panelSecondary().panelPadding()
            .panel("Right panel with a gutter between panel and content.")
            .contentPadding().content("pf-m-panel-right pf-m-gutter combine placement and spacing.")
            .build();

    public static Sidebar demoSbRight = Sidebar.of("sb-right")
            .panelRight().gutter().contentFirst()
            .contentStyle("padding: 1rem").content("Main content on the left.")
            .panelStyle(SECONDARY_BG)
            .panel("Panel on the right — useful for activity, related items, or contextual help.")
            .build();

    public static Sidebar demoSbResponsive = Sidebar.of("sb-responsive-panel")
            .panelSecondary().panelPadding()
            .panelWidths("pf-m-width-25 pf-m-width-33-on-lg pf-m-width-50-on-xl")
            .panel("Responsive width — 25% by default, 33% on lg, 50% on xl.")
            .contentPadding().content("The panel width modifiers take breakpoint suffixes.")
            .build();

    public static Sidebar demoSbStack = Sidebar.of("sb-stack").stack()
            .panelSecondary().panelPadding()
            .panel("Stacked panel — sits above the content instead of beside it.")
            .contentPadding().content("Content below the stacked panel (pf-m-stack).")
            .build();

    public static Sidebar demoSbStatic = Sidebar.of("sb-static-panel")
            .panelStatic().panelSecondary().panelPadding()
            .panel("Static panel — scrolls away with the content (pf-m-static).")
            .contentPadding().content(SCROLL_ROWS)
            .build();

    public static Sidebar demoSbSticky = Sidebar.of("sb-sticky-panel")
            .panelSticky().panelSecondary().panelPadding()
            .panel("Sticky panel — stays pinned while the content scrolls.")
            .contentPadding().content(SCROLL_ROWS)
            .build();
}
