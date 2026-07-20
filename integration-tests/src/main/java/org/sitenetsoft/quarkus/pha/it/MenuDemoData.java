package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Menu;
import org.sitenetsoft.quarkus.pha.model.MenuItem;

/**
 * Demo data for the menu examples — every model-driven example on
 * /components/menu is populated from one of these Menu models. The drilldown
 * family, flyouts and the favorites example stay hand-written: their live
 * multi-level / list-mutating Alpine state is a composition outside the
 * model's scope. Globals so the standalone example route (which renders
 * templates without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/menu/ served on the example card's Java tab — keep
 * them in sync when editing.
 */
@TemplateGlobal
public class MenuDemoData {

    public static Menu demoMenuBasic = Menu.builder()
            .id("mn-basic").style("max-width: 220px")
            .item(MenuItem.of("Action 1"))
            .item(MenuItem.of("Action 2"))
            .item(MenuItem.of("Disabled action").asDisabled())
            .item(MenuItem.divider())
            .item(MenuItem.of("Delete").asDanger())
            .build();

    public static Menu demoMenuDangerItem = Menu.builder()
            .id("mn-danger-item").style("max-width: 220px")
            .item(MenuItem.of("Action 1"))
            .item(MenuItem.of("Action 2"))
            .item(MenuItem.divider())
            .item(MenuItem.of("Delete").asDanger().srPrefix("Danger item:"))
            .build();

    public static Menu demoMenuWithIcons = Menu.builder()
            .id("mn-icons").style("max-width: 240px")
            .item(MenuItem.of("From git").icon("fa:code"))
            .item(MenuItem.of("Container image").icon("fa:cube"))
            .item(MenuItem.of("Docker file").icon("fa:file-code"))
            .build();

    public static Menu demoMenuWithLinks = Menu.builder()
            .id("mn-links").style("max-width: 220px")
            .item(MenuItem.of("Link 1").href("#").asExternal())
            .item(MenuItem.of("Link 2").href("#").asExternal())
            .item(MenuItem.of("Link 3").href("#"))
            .build();

    public static Menu demoMenuWithDescriptions = Menu.builder()
            .id("mn-descriptions").style("max-width: 280px")
            .item(MenuItem.of("Edit").description("Change the name or labels."))
            .item(MenuItem.of("Duplicate").description("Create a copy in this project."))
            .item(MenuItem.of("Delete").description("Permanently remove the resource.").asDanger())
            .build();

    public static Menu demoMenuPlain = Menu.builder()
            .id("mn-plain").plain().style("max-width: 220px")
            .item(MenuItem.of("Action"))
            .item(MenuItem.of("Link").href("#"))
            .item(MenuItem.of("Disabled action").asDisabled())
            .item(MenuItem.of("Disabled link").href("#").asDisabled())
            .build();

    public static Menu demoMenuTitledGroups = Menu.builder()
            .id("mn-titled-groups").style("max-width: 260px")
            .group(Menu.group(null,
                    MenuItem.of("All resources")))
            .group(Menu.group("Group 1",
                    MenuItem.of("Pods"),
                    MenuItem.of("Deployments")))
            .group(Menu.group("Group 2",
                    MenuItem.of("Services"),
                    MenuItem.of("Routes")))
            .build();

    public static Menu demoMenuSeparatedItems = Menu.builder()
            .id("mn-separated").style("max-width: 220px")
            .item(MenuItem.of("Action 1"))
            .item(MenuItem.divider())
            .item(MenuItem.of("Action 2"))
            .item(MenuItem.divider())
            .item(MenuItem.of("Action 3"))
            .build();

    public static Menu demoMenuScrollable = Menu.builder()
            .id("mn-scrollable").scrollable().style("max-width: 220px")
            .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                    MenuItem.of("Action 4"), MenuItem.of("Action 5"), MenuItem.of("Action 6"),
                    MenuItem.of("Action 7"), MenuItem.of("Action 8"), MenuItem.of("Action 9"),
                    MenuItem.of("Action 10"), MenuItem.of("Action 11"), MenuItem.of("Action 12"))
            .build();

    public static Menu demoMenuScrollableCustomHeight = Menu.builder()
            .id("mn-scrollable-custom-height").scrollable().style("max-width: 220px")
            .contentStyle("--pf-v6-c-menu__content--MaxHeight: 200px")
            .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                    MenuItem.of("Action 4"), MenuItem.of("Action 5"), MenuItem.of("Action 6"),
                    MenuItem.of("Action 7"), MenuItem.of("Action 8"), MenuItem.of("Action 9"),
                    MenuItem.of("Action 10"), MenuItem.of("Action 11"), MenuItem.of("Action 12"))
            .build();

    public static Menu demoMenuFooter = Menu.builder()
            .id("mn-footer").style("max-width: 220px")
            .item(MenuItem.of("Action"))
            .item(MenuItem.of("Link").href("#"))
            .footerButton("Footer action")
            .build();

    public static Menu demoMenuHeaderFooter = Menu.builder()
            .id("mn-header-footer").scrollable().style("max-width: 220px")
            .header("Header")
            .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                    MenuItem.of("Action 4"), MenuItem.of("Action 5"), MenuItem.of("Action 6"),
                    MenuItem.of("Action 7"), MenuItem.of("Action 8"), MenuItem.of("Action 9"),
                    MenuItem.of("Action 10"), MenuItem.of("Action 11"), MenuItem.of("Action 12"))
            .footerButton("Footer")
            .build();

    public static Menu demoMenuLoading = Menu.builder()
            .id("mn-loading").style("max-width: 220px")
            .item(MenuItem.of("Action"))
            .item(MenuItem.of("Link").href("#"))
            .item(MenuItem.of("Disabled action").asDisabled())
            .item(MenuItem.of("Disabled link").href("#").asDisabled())
            .item(MenuItem.divider())
            .loading("Loading items")
            .build();

    public static Menu demoMenuWithActions = Menu.builder()
            .id("mn-actions").plain().style("max-width: 260px")
            .item(MenuItem.of("Item 1").asFavoriteAction())
            .item(MenuItem.of("Item 2").action("fa:clipboard", "Copy"))
            .item(MenuItem.of("Item 3").action("fa:bell", "Notifications"))
            .build();

    public static Menu demoMenuItemCheckbox = Menu.builder()
            .id("mn-item-checkbox").style("max-width: 260px")
            .checkboxes("mn-check")
            .item(MenuItem.of("Checkbox 1"))
            .item(MenuItem.of("Checkbox 2").asSelected())
            .item(MenuItem.of("Checkbox 3"))
            .build();

    public static Menu demoMenuOptionSingleSelect = Menu.builder()
            .id("mn-option-single-select").style("max-width: 260px")
            .selectSingle()
            .item(MenuItem.of("Option 1").asSelected())
            .item(MenuItem.of("Option 2"))
            .item(MenuItem.of("Option 3"))
            .build();

    public static Menu demoMenuOptionMultiSelect = Menu.builder()
            .id("mn-option-multi-select").style("max-width: 260px")
            .selectMulti()
            .item(MenuItem.of("Option 1").asSelected())
            .item(MenuItem.of("Option 2"))
            .item(MenuItem.of("Option 3"))
            .build();

    public static Menu demoMenuViewMore = Menu.builder()
            .id("mn-view-more").style("max-width: 260px")
            .viewMore("View more", 3, "Loaded action ")
            .item(MenuItem.of("Action 1"))
            .item(MenuItem.of("Action 2"))
            .item(MenuItem.of("Action 3"))
            .build();

    public static Menu demoMenuFilteringSearch = Menu.builder()
            .id("mn-filtering-search").style("max-width: 260px")
            .searchFilter("Search", "Search menu items")
            .item(MenuItem.of("Action 1"))
            .item(MenuItem.of("Action 2"))
            .item(MenuItem.of("Action 3"))
            .build();

    public static Menu demoMenuSearchFooter = Menu.builder()
            .id("mn-search-footer").scrollable().style("max-width: 220px")
            .searchFilter("Search", "Search menu items")
            .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                    MenuItem.of("Action 4"), MenuItem.of("Action 5"), MenuItem.of("Action 6"),
                    MenuItem.of("Action 7"), MenuItem.of("Action 8"), MenuItem.of("Action 9"),
                    MenuItem.of("Action 10"), MenuItem.of("Action 11"), MenuItem.of("Action 12"))
            .footerButton("Footer")
            .build();

    public static Menu demoMenuPlainSearchFooter = Menu.builder()
            .id("mn-plain-search-footer").plain().style("max-width: 220px")
            .searchFilter("Search", "Search menu items")
            .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                    MenuItem.of("Action 4"), MenuItem.of("Action 5"), MenuItem.of("Action 6"),
                    MenuItem.of("Action 7"), MenuItem.of("Action 8"), MenuItem.of("Action 9"),
                    MenuItem.of("Action 10"), MenuItem.of("Action 11"), MenuItem.of("Action 12"))
            .footerButton("Footer").footerDivider()
            .build();

    public static Menu demoMenuPlainScrollableSearchFooter = Menu.builder()
            .id("mn-plain-scrollable-search-footer").plain().scrollable().style("max-width: 220px")
            .searchFilter("Search", "Search menu items")
            .items(MenuItem.of("Action 1"), MenuItem.of("Action 2"), MenuItem.of("Action 3"),
                    MenuItem.of("Action 4"), MenuItem.of("Action 5"), MenuItem.of("Action 6"),
                    MenuItem.of("Action 7"), MenuItem.of("Action 8"), MenuItem.of("Action 9"),
                    MenuItem.of("Action 10"), MenuItem.of("Action 11"), MenuItem.of("Action 12"))
            .footerButton("Footer")
            .build();
}
