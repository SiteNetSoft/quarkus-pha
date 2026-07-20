package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.MenuToggle;
import org.sitenetsoft.quarkus.pha.model.Toolbar;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

import java.util.List;

/**
 * Demo data for the toolbar examples — the examples on /components/toolbar are
 * populated from these models (stacked plus the live filter/label-group
 * compositions — custom-label-group-content, dynamic-sticky, toggle-groups,
 * with-filters, label-group — stay hand-written). Globals so the standalone
 * example route (which renders templates without data) can see them. Each is
 * mirrored by a snippet in resources/code-samples/toolbar/ served on the
 * example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ToolbarDemoData {

    private static Item secondaryBtn(String text) {
        return Item.button(Button.of(text).variant("secondary").build());
    }

    public static Toolbar demoTbBasic = Toolbar.of("tb-basic")
            .section(Section.of(
                    Entry.group(Group.of(
                            Item.search("Search"),
                            secondaryBtn("Filter"))),
                    Entry.group(Group.of(
                            Item.button(Button.of("Create").variant("primary").build()),
                            Item.button(Button.icon("fa:ellipsis-vertical", "More actions").variant("plain").build()))
                            .alignEnd())))
            .build();

    public static Toolbar demoTbItems = Toolbar.of("tb-items")
            .section(Section.of(
                    Entry.item(Item.search("Search")),
                    Entry.item(secondaryBtn("Action")),
                    Entry.dividerDiv(),
                    Entry.item(Item.button(Button.of("Primary action").variant("primary").build())),
                    Entry.item(Item.button(Button.icon("fa:ellipsis-vertical", "More options").variant("plain").build()))))
            .build();

    public static Toolbar demoTbActionGroup = Toolbar.of("tb-action-group")
            .section(Section.of(Entry.group(Group.of(
                    Item.button(Button.of("Primary").variant("primary").build()),
                    secondaryBtn("Secondary"),
                    Item.button(Button.of("Tertiary").variant("tertiary").build()))
                    .modifiers("pf-m-action-group"))))
            .build();

    public static Toolbar demoTbActionGroupInline = Toolbar.of("tb-action-group-inline")
            .section(Section.of(Entry.group(Group.of(
                    Item.text("6 filters applied"),
                    Item.button(Button.of("Clear all filters").variant("link").asInline().build()),
                    Item.button(Button.of("Save all filters").variant("link").asInline().build()))
                    .modifiers("pf-m-action-group-inline"))))
            .build();

    public static Toolbar demoTbActionGroupPlain = Toolbar.of("tb-action-group-plain")
            .section(Section.of(Entry.group(Group.of(
                    Item.button(Button.icon("fa:table-columns", "Column view").variant("plain").build()),
                    Item.button(Button.icon("fa:expand", "Expand").variant("plain").build()),
                    Item.button(Button.icon("fa:gear", "Settings").variant("plain").build()))
                    .modifiers("pf-m-action-group-plain"))))
            .build();

    public static List<Toolbar> demoTbsColors = List.of(
            Toolbar.of("tb-color-default")
                    .section(Section.of(Entry.item(secondaryBtn("Default background")))).build(),
            Toolbar.of("tb-color-primary").variant("primary")
                    .section(Section.of(Entry.item(secondaryBtn("Primary background")))).build(),
            Toolbar.of("tb-color-secondary").variant("secondary")
                    .section(Section.of(Entry.item(secondaryBtn("Secondary background")))).build(),
            Toolbar.of("tb-color-none").noBackground()
                    .section(Section.of(Entry.item(secondaryBtn("No background")))).build());

    public static Toolbar demoTbContentWrap = Toolbar.of("tb-content-wrap")
            .section(Section.of(
                    Entry.item(secondaryBtn("Wrapping action 1")),
                    Entry.item(secondaryBtn("Wrapping action 2")),
                    Entry.item(secondaryBtn("Wrapping action 3")),
                    Entry.item(secondaryBtn("Wrapping action 4")),
                    Entry.item(secondaryBtn("Wrapping action 5")),
                    Entry.item(secondaryBtn("Wrapping action 6")))
                    .modifiers("pf-m-wrap"))
            .build();

    private static final String OUTLINE =
            "outline: 1px dashed var(--pf-t--global--border--color--default)";

    public static Toolbar demoTbGroupSpacers = Toolbar.of("tb-group-spacers")
            .section(Section.of(
                    Entry.group(Group.of(secondaryBtn("gap-none 1"), secondaryBtn("gap-none 2"))
                            .columnGap("none").style(OUTLINE)),
                    Entry.group(Group.of(secondaryBtn("gap-2xl 1"), secondaryBtn("gap-2xl 2"))
                            .columnGap("2xl").style(OUTLINE))))
            .build();

    public static Toolbar demoTbGroups = Toolbar.of("tb-groups")
            .section(Section.of(
                    Entry.group(Group.of(
                            Item.toggle(MenuToggle.of("Status").build()),
                            Item.toggle(MenuToggle.of("Risk").build())).variant("filter")),
                    Entry.group(Group.of(
                            Item.button(Button.icon("fa:clone", "Clone").variant("plain").build()),
                            Item.button(Button.icon("fa:arrows-rotate", "Sync").variant("plain").build()),
                            Item.button(Button.icon("fa:pen-to-square", "Edit").variant("plain").build()))),
                    Entry.group(Group.of(
                            Item.button(Button.of("Create").variant("primary").build()),
                            secondaryBtn("Secondary")).alignEnd())))
            .build();

    public static Toolbar demoTbInsets = Toolbar.of("tb-insets")
            .modifiers("pf-m-inset-md pf-m-inset-xl-on-lg")
            .section(Section.of(
                    Entry.item(secondaryBtn("Inset md")),
                    Entry.item(secondaryBtn("Action 2"))))
            .build();

    public static Toolbar demoTbItemSpacers = Toolbar.of("tb-item-spacers")
            .section(Section.of(
                    Entry.item(secondaryBtn("Default gap")),
                    Entry.item(secondaryBtn("gap-none").gap("none")),
                    Entry.item(secondaryBtn("gap-xl").gap("xl")),
                    Entry.item(secondaryBtn("gap-4xl").gap("4xl"))))
            .build();

    public static Toolbar demoTbNoPadding = Toolbar.of("tb-no-padding")
            .modifiers("pf-m-no-padding")
            .section(Section.of(
                    Entry.item(Item.text("Item")),
                    Entry.item(Item.text("Item")),
                    Entry.item(Item.text("Item")),
                    Entry.dividerHr(),
                    Entry.group(Group.of(Item.text("Item"), Item.text("Item")))))
            .build();

    public static Toolbar demoTbSticky = Toolbar.of("tb-sticky").sticky()
            .section(Section.of(
                    Entry.item(Item.search("Search")),
                    Entry.item(secondaryBtn("Action"))))
            .build();

    public static Toolbar demoTbVertical = Toolbar.of("tb-vertical").vertical()
            .section(Section.of(
                    Entry.item(Item.button(Button.icon("fa:table-cells-large", "Grid view").variant("plain").build())),
                    Entry.item(Item.button(Button.icon("fa:filter", "Filter").variant("plain").build())),
                    Entry.item(Item.button(Button.icon("fa:gear", "Settings").variant("plain").build()))))
            .build();

    public static Toolbar demoTbWidthControl = Toolbar.of("tb-width-control")
            .section(Section.of(
                    Entry.group(Group.of(
                            Item.text("80px / 10rem on xl")
                                    .style("--pf-v6-c-toolbar__item--Width: 80px;"
                                            + " --pf-v6-c-toolbar__item--Width-on-xl: 10rem; " + OUTLINE),
                            Item.text("Item"))),
                    Entry.dividerHr(),
                    Entry.item(Item.text("Item"))))
            .build();

    public static List<Toolbar> demoTbsFilterGroup = List.of(
            Toolbar.of("tb-filter-group")
                    .section(Section.of(Entry.group(Group.of(
                            Item.toggle(MenuToggle.of("Status").build()),
                            Item.toggle(MenuToggle.of("Risk").build())).variant("filter")))).build(),
            Toolbar.of("tb-filter-group-input")
                    .section(Section.of(Entry.group(Group.of(
                            Item.search("Filter by name"),
                            Item.toggle(MenuToggle.of("Status").build()))))).build());
}
