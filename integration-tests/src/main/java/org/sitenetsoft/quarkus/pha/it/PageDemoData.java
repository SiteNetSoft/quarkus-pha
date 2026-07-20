package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Breadcrumb;
import org.sitenetsoft.quarkus.pha.model.BreadcrumbItem;
import org.sitenetsoft.quarkus.pha.model.Page;
import org.sitenetsoft.quarkus.pha.model.Page.NavItem;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

/**
 * Demo data for the page examples — all examples on /components/page are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/page/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class PageDemoData {

    private static String border(String h) {
        return h + "; border: 1px solid var(--pf-t--global--border--color--default)";
    }

    public static Page demoPageBasic = Page.of("pg-basic").style(border("min-height: 360px"))
            .brand("App name")
            .section(Section.of("""
                    <h2 class="pf-v6-c-title pf-m-2xl">Welcome</h2>
                    <p style="margin-top: 1rem">Page content lives inside <code>pf-v6-c-page__main</code>. Combine with the Layouts components (Sidebar, Stack, Flex) to structure the body.</p>""")
                    .bodyStyle("padding: 1rem"))
            .build();

    public static Page demoPageCentered = Page.of("pg-centered-section").style(border("min-height: 320px"))
            .brand("Logo")
            .section(Section.of("""
                    <div class="pf-v6-c-card">
                      <div class="pf-v6-c-card__body">
                        When a width-limited page section is wider than the value of
                        <code class="ws-code">--pf-v6-c-page--section--m-limit-width--MaxWidth</code>, the section is centered in
                        the main area (<code class="ws-code">pf-m-limit-width pf-m-align-center</code>).
                      </div>
                    </div>""")
                    .modifiers("pf-m-limit-width pf-m-align-center"))
            .build();

    public static Page demoPageFilled = Page.of("pg-filled-sections").style(border("min-height: 480px"))
            .brand("Logo")
            .section(Section.of("Default fill behavior — the section sizes to its content."))
            .section(Section.of("<code class=\"ws-code\">pf-m-fill</code> — this section grows to fill"
                    + " the available vertical space.").modifiers("pf-m-fill"))
            .section(Section.of("<code class=\"ws-code\">pf-m-no-fill</code> — this section never grows"
                    + " beyond its content.").modifiers("pf-m-no-fill"))
            .build();

    public static Page demoPagePadding = Page.of("pg-main-section-padding").style(border("min-height: 360px"))
            .brand("Logo")
            .section(Section.of("Section with default padding."))
            .section(Section.of("Section with <code class=\"ws-code\">pf-m-no-padding</code> —"
                    + " content runs edge to edge.").modifiers("pf-m-no-padding"))
            .section(Section.of("Section with <code class=\"ws-code\">pf-m-no-padding pf-m-padding-on-md</code>"
                    + " — padding returns at the md breakpoint.").modifiers("pf-m-no-padding pf-m-padding-on-md"))
            .build();

    public static Page demoPageVariations = Page.of("pg-main-section-variations").style(border("min-height: 420px"))
            .brand("Logo")
            .region("subnav", "With subnav type",
                    "Element with <code class=\"ws-code\">pf-v6-c-page__main-subnav</code> for horizontal subnav navigation")
            .region("tabs", "With tabs type",
                    "Element with <code class=\"ws-code\">pf-v6-c-page__main-tabs</code> for tabs")
            .region("breadcrumb", "With breadcrumb type",
                    "Element with <code class=\"ws-code\">pf-v6-c-page__main-breadcrumb</code> for breadcrumbs")
            .section(Section.of("Element with <code class=\"ws-code\">pf-v6-c-page__main-section</code>"
                    + " for main content sections").ariaLabel("With default type"))
            .region("wizard", "With wizard type",
                    "Element with <code class=\"ws-code\">pf-v6-c-page__main-wizard</code> for wizards")
            .build();

    public static Page demoPagePlain = Page.of("pg-plain-sections").style(border("min-height: 480px"))
            .brand("Logo")
            .section(Section.of("Plain section — <code class=\"ws-code\">pf-m-plain</code> removes"
                    + " the background color.").modifiers("pf-m-plain"))
            .section(Section.of("Default section with the standard background."))
            .group(true, null,
                    Section.of("Section inside a <code class=\"ws-code\">pf-v6-c-page__main-group pf-m-plain</code>."),
                    Section.of("Another section in the same plain group."))
            .section(Section.of("Secondary variant section (<code class=\"ws-code\">pf-m-secondary</code>)"
                    + " for contrast.").modifiers("pf-m-secondary"))
            .build();

    public static Page demoPageGroup = Page.of("pg-group-section").style(border("min-height: 400px"))
            .brand("Logo")
            .group(false,
                    Breadcrumb.of("pg-group-breadcrumb")
                            .item(BreadcrumbItem.of("Section home").href("#"))
                            .item(BreadcrumbItem.of("Section title").href("#").asCurrent()).build(),
                    Section.of("The breadcrumb and this section are wrapped in a single"
                            + " <code class=\"ws-code\">pf-v6-c-page__main-group</code>, so they read"
                            + " as one visual unit."))
            .section(Section.of("A separate section outside the group.").modifiers("pf-m-secondary"))
            .build();

    public static Page demoPageSticky = Page.of("pg-dynamic-sticky-section").style(border("height: 360px"))
            .brand("Logo").scrollableMain()
            .section(Section.of("Sticky section (<code class=\"ws-code\">pf-m-sticky-top</code>) — scroll"
                    + " the area below; this stays pinned. Height-based variants like"
                    + " <code class=\"ws-code\">pf-m-sticky-top-on-lg-height</code> make it conditional.")
                    .modifiers("pf-m-sticky-top pf-m-secondary"))
            .section(Section.of("""
                    <p>Scrollable content 1</p>
                    <p style="margin-top: 4rem">Scrollable content 2</p>
                    <p style="margin-top: 4rem">Scrollable content 3</p>
                    <p style="margin-top: 4rem">Scrollable content 4</p>
                    <p style="margin-top: 4rem">Scrollable content 5</p>
                    <p style="margin-top: 4rem">Scrollable content 6</p>"""))
            .build();

    public static Page demoPageHorizontalNav = Page.of("pg-horizontal-nav").style(border("min-height: 320px"))
            .brand("Logo").mastheadInline()
            .horizontalNav("Global",
                    NavItem.of("Horizontal nav item 1").current(),
                    NavItem.of("Horizontal nav item 2"),
                    NavItem.of("Horizontal nav item 3"))
            .section(Section.of("The navigation lives in the masthead content area as a horizontal"
                    + " <code class=\"ws-code\">pf-v6-c-nav pf-m-horizontal</code> — no sidebar."))
            .build();

    public static Page demoPageUncontrolled = Page.of("pg-uncontrolled-nav").style(border("min-height: 360px"))
            .brand("Logo").toggle("Uncontrolled nav demo")
            .sidebarNav("Uncontrolled nav demo secondary",
                    NavItem.of("Nav item 1").current(), NavItem.of("Nav item 2"), NavItem.of("Nav item 3"))
            .section(Section.of("The sidebar state is owned entirely by the page shell (local Alpine"
                    + " state) — no outside component controls it. Click the burger to toggle."))
            .build();

    public static Page demoPageVerticalNav = Page.of("pg-vertical-nav").style(border("min-height: 400px"))
            .brand("Logo").toggle("Vertical nav demo").toolbarText("header-tools")
            .sidebarNav("Vertical nav demo secondary",
                    NavItem.of("Nav item 1").current(), NavItem.of("Nav item 2"), NavItem.of("Nav item 3"))
            .section(Section.of("<h2 id=\"pg-vertical-nav-s1\" class=\"pf-v6-c-title pf-m-xl\">Vertical nav"
                    + " example section 1</h2>").ariaLabelledBy("pg-vertical-nav-s1"))
            .section(Section.of("""
                    <h2 id="pg-vertical-nav-s2" class="pf-v6-c-title pf-m-xl">
                      Vertical nav example section 2 with secondary variant styling
                    </h2>""").modifiers("pf-m-secondary").ariaLabelledBy("pg-vertical-nav-s2"))
            .section(Section.of("<h2 id=\"pg-vertical-nav-s3\" class=\"pf-v6-c-title pf-m-xl\">Vertical nav"
                    + " example section 3</h2>").ariaLabelledBy("pg-vertical-nav-s3"))
            .build();

    public static Page demoPageMultiBody = Page.of("pg-multiple-sidebar-body").style(border("min-height: 400px"))
            .brand("Logo").toggle("Multiple sidebar body demo").toolbarText("header-tools")
            .sidebarContextSelector("First sidebar body (for a context selector/perspective switcher)")
            .sidebarNavFill()
            .sidebarNav("Multiple sidebar body demo secondary",
                    NavItem.of("Nav item 1").current(), NavItem.of("Nav item 2"), NavItem.of("Nav item 3"))
            .section(Section.of("The sidebar stacks two <code class=\"ws-code\">pf-v6-c-page__sidebar-body</code>"
                    + " elements — the first with <code class=\"ws-code\">pf-m-context-selector</code>, the second"
                    + " with <code class=\"ws-code\">pf-m-fill</code> to take the remaining height."))
            .build();
}
