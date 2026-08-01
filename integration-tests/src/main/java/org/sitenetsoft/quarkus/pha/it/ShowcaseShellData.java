package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.JumpLinkItem;
import org.sitenetsoft.quarkus.pha.model.JumpLinks;
import org.sitenetsoft.quarkus.pha.model.Menu;
import org.sitenetsoft.quarkus.pha.model.MenuItem;
import org.sitenetsoft.quarkus.pha.model.Nav;
import org.sitenetsoft.quarkus.pha.model.NavEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Shared data for the showcase shell (partials/showcase-page.html): the
 * sidebar navigation and the masthead search menu. Both are derived from the
 * same registries that drive the /components grid, the layout routes, and the
 * pattern routes, so the shell can never drift from the real page inventory.
 */
@TemplateGlobal
public class ShowcaseShellData {

    private static String capitalize(String slug) {
        return Character.toUpperCase(slug.charAt(0)) + slug.substring(1);
    }

    /** name → href for every implemented component page, sorted by name. */
    private static Map<String, String> componentPages() {
        Map<String, String> pages = new TreeMap<>();
        for (Map<String, String> comp : HelloResource.buildComponentList()) {
            String href = comp.get("href");
            if (href != null && href.startsWith("/components/")) {
                pages.put(comp.get("name"), href);
            }
        }
        return pages;
    }

    /** name → href for every extension page, sorted by name. */
    private static Map<String, String> extensionPages() {
        Map<String, String> pages = new TreeMap<>();
        for (Map<String, String> comp : HelloResource.buildComponentList()) {
            String href = comp.get("href");
            if (href != null && href.startsWith("/extensions/")) {
                pages.put(comp.get("name"), href);
            }
        }
        return pages;
    }

    private static Map<String, String> layoutPages() {
        Map<String, String> pages = new TreeMap<>();
        for (String slug : LayoutsRoutes.layoutNames()) {
            pages.put(capitalize(slug), "/layouts/" + slug);
        }
        return pages;
    }

    private static Map<String, String> patternPages() {
        Map<String, String> pages = new TreeMap<>();
        for (Map.Entry<String, String> e : PatternsRoutes.titles().entrySet()) {
            pages.put(e.getValue(), "/patterns/" + e.getKey());
        }
        return pages;
    }

    private static NavEntry[] entries(Map<String, String> pages) {
        return entries(pages, null);
    }

    /** overviewHref, when set, becomes a leading "Overview" entry (patternfly.org-style). */
    private static NavEntry[] entries(Map<String, String> pages, String overviewHref) {
        List<NavEntry> items = new ArrayList<>();
        if (overviewHref != null) {
            items.add(Nav.item("Overview", overviewHref));
        }
        for (Map.Entry<String, String> e : pages.entrySet()) {
            items.add(Nav.item(e.getKey(), e.getValue()));
        }
        return items.toArray(new NavEntry[0]);
    }

    /** Sidebar navigation: top pages plus expandable per-category page lists. */
    public static Nav showcaseNav = Nav.builder()
            .id("showcase-nav")
            .ariaLabel("Showcase navigation")
            .item("Home", "/")
            .expandable("All components", entries(componentPages(), "/components"))
            .expandable("Extensions", entries(extensionPages()))
            .expandable("Layouts", entries(layoutPages()))
            .expandable("Patterns", entries(patternPages()))
            .item("JSON view models", "/json-models")
            .item("Licenses", "/licenses")
            .build();

    /** Masthead search: a search-filter menu over every showcase page. */
    public static Menu showcaseSearchMenu = buildSearchMenu();

    /** ToC for the backdrop demo page (showcase-shell pilot). */
    public static JumpLinks tocBackdrop = ShowcaseToc.of("backdrop",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With content", "#with-content"))
                    .sub(JumpLinkItem.of("Non-dismissible", "#non-dismissible")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Backdrop props", "#props-backdrop"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    private static Menu buildSearchMenu() {
        Menu.Builder b = Menu.builder()
                .id("pha-global-search-menu")
                .scrollable()
                .style("max-height: 60vh; overflow-y: auto")
                .searchFilter("Search the showcase", "Search all pages");
        componentPages().forEach((name, href) -> b.item(MenuItem.of(name).href(href)));
        extensionPages().forEach((name, href) -> b.item(MenuItem.of(name).href(href)));
        layoutPages().forEach((name, href) -> b.item(MenuItem.of(name).href(href)));
        patternPages().forEach((name, href) -> b.item(MenuItem.of(name).href(href)));
        b.item(MenuItem.of("JSON view models").href("/json-models"));
        return b.build();
    }
}
