package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.ToggleGroup;
import org.sitenetsoft.quarkus.pha.model.ToggleGroup.Item;

/**
 * Demo data for the toggle-group examples — the examples on
 * /components/toggle-group are populated from these models; the selection
 * Alpine (single- and multi-select) is generated at build(). Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/toggle-group/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ToggleGroupDemoData {

    public static ToggleGroup demoTgCompact = ToggleGroup.of("tg-compact", "Toggle group").compact()
            .item(Item.of("Hourly")).item(Item.of("Daily")).item(Item.of("Weekly")).build();

    public static ToggleGroup demoTgFullWidth = ToggleGroup.of("tg-full-width", "Toggle group").fill()
            .item(Item.of("Development")).item(Item.of("Staging")).item(Item.of("Production")).build();

    public static ToggleGroup demoTgMulti = ToggleGroup.of("tg-multi", "Filters").multiVar("filters")
            .item(Item.of("Bold").key("bold"))
            .item(Item.of("Italic").key("italic").pressed())
            .item(Item.of("Underline").key("underline")).build();

    public static ToggleGroup demoTgSingle = ToggleGroup.of("tg-single", "View mode")
            .stateVar("selected").selectedKey("list")
            .item(Item.of("List").key("list"))
            .item(Item.of("Grid").key("grid"))
            .item(Item.of("Table").key("table")).build();

    public static ToggleGroup demoTgTextIcons = ToggleGroup.of("tg-text-icons", "Toggle group")
            .item(Item.of("Copy").withIcon("fa:copy"))
            .item(Item.of("Paste").withIcon("fa:clipboard"))
            .item(Item.of("Print").withIcon("fa:print")).build();

    public static ToggleGroup demoTgIcons = ToggleGroup.of("tg-icons", "Alignment").stateVar("align")
            .item(Item.icon("fa:align-left", "Align left").key("left"))
            .item(Item.icon("fa:align-center", "Align center").key("center"))
            .item(Item.icon("fa:align-right", "Align right").key("right")).build();
}
