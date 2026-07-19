package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.DataList;
import org.sitenetsoft.quarkus.pha.model.DataListCell;
import org.sitenetsoft.quarkus.pha.model.TableAction;

/**
 * Demo data for the data-list examples — every model-driven example on
 * /components/data-list is populated from one of these DataList models.
 * Globals so the standalone example route (which renders templates without
 * data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/data-list/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class DataListDemoData {

    public static DataList demoBasicList = DataList.builder()
            .id("dl-basic").ariaLabel("Data list")
            .item(DataList.item(DataListCell.title("Project Apollo").description("Backend service"),
                    "Active", "2 hours ago"))
            .item(DataList.item(DataListCell.title("Mercury").description("Web frontend"),
                    "Active", "5 hours ago"))
            .item(DataList.item(DataListCell.title("Gemini").description("Data pipeline"),
                    "Paused", "Yesterday"))
            .build();

    public static DataList demoCompactList = DataList.builder()
            .id("dl-compact").ariaLabel("Data list").compact()
            .item(DataList.item("user-1@example.com", "Admin"))
            .item(DataList.item("user-2@example.com", "Member"))
            .item(DataList.item("user-3@example.com", "Viewer"))
            .build();

    public static DataList demoPlainList = DataList.builder()
            .id("dl-plain").ariaLabel("Data list").plain()
            .item(DataList.item(DataListCell.title("Primary content"), "Secondary content"))
            .item(DataList.item(DataListCell.title("Second row"), "More content"))
            .build();

    public static DataList demoAsGridList = DataList.builder()
            .id("dl-as-grid").ariaLabel("Data list as grid").modifier("pf-m-grid")
            .item(DataList.item(DataListCell.title("Project Apollo"), "Backend service", "Active", "2 hours ago"))
            .item(DataList.item(DataListCell.title("Mercury"), "Web frontend", "Active", "5 hours ago"))
            .build();

    public static DataList demoNoGridList = DataList.builder()
            .id("dl-no-grid").ariaLabel("Data list with no grid").modifier("pf-m-grid-none")
            .item(DataList.item(DataListCell.title("Project Apollo"), "Backend service", "Active", "2 hours ago"))
            .item(DataList.item(DataListCell.title("Mercury"), "Web frontend", "Active", "5 hours ago"))
            .build();

    public static DataList demoSmGridList = DataList.builder()
            .id("dl-sm-grid").ariaLabel("Data list").gridBreakpoint("sm")
            .item(DataList.item(DataListCell.title("Grid collapses below sm"), "Cell two", "Cell three"))
            .item(DataList.item(DataListCell.title("Second row"), "Cell two", "Cell three"))
            .build();

    public static DataList demoWithHeadingsList = DataList.builder()
            .id("dl-with-headings").ariaLabel("Data list with headings")
            .item(DataList.item(DataListCell.heading("Primary content"), "Secondary content"))
            .item(DataList.item(
                    DataListCell.heading("Secondary content (pf-m-no-fill)").withModifier("pf-m-no-fill"),
                    DataListCell.text("Secondary content (pf-m-align-right pf-m-no-fill)")
                            .withModifier("pf-m-no-fill").withModifier("pf-m-align-right")))
            .build();

    public static DataList demoWidthModifiersList = DataList.builder()
            .id("dl-width-modifiers").ariaLabel("Data list")
            .item(DataList.item(DataListCell.title("default"),
                    DataListCell.text("pf-m-flex-2 takes twice the space").withModifier("pf-m-flex-2"),
                    DataListCell.text("pf-m-flex-3").withModifier("pf-m-flex-3")))
            .item(DataList.item(DataListCell.title("default"),
                    DataListCell.text("pf-m-flex-4 takes four times the space").withModifier("pf-m-flex-4")))
            .item(DataList.item(DataListCell.title("default"),
                    DataListCell.text("pf-m-flex-5 takes five times the space").withModifier("pf-m-flex-5")))
            .item(DataList.item(DataListCell.text("pf-m-no-fill keeps to its content").withModifier("pf-m-no-fill"),
                    "default fills the remaining space"))
            .build();

    public static DataList demoControllingTextList = DataList.builder()
            .id("dl-controlling-text").ariaLabel("Data list")
            .item(DataList.item(
                    DataListCell.text("pf-m-break-word").withModifier("pf-m-break-word").id("dl-text-break"),
                    DataListCell.text("ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-")
                            .withModifier("pf-m-break-word")))
            .item(DataList.item(
                    DataListCell.text("pf-m-truncate").withModifier("pf-m-truncate").id("dl-text-truncate"),
                    DataListCell.text("ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-ReallyLongWordThatWouldOverflowTheCellWithoutAModifier-")
                            .withModifier("pf-m-truncate")))
            .item(DataList.item(
                    DataListCell.text("pf-m-nowrap").withModifier("pf-m-nowrap").id("dl-text-nowrap"),
                    DataListCell.text("This cell refuses to wrap its content onto a second line.")
                            .withModifier("pf-m-nowrap")))
            .build();

    public static DataList demoActionsList = DataList.builder()
            .id("dl-actions").ariaLabel("Data list")
            .item(DataList.item(DataListCell.title("Single action row"), "A lone action button")
                    .action(TableAction.secondary("Action")))
            .item(DataList.item(DataListCell.title("Multiple actions row"), "A kebab menu of actions")
                    .kebab("Row actions", TableAction.link("Edit"), TableAction.danger("Delete")))
            .build();

    public static DataList demoCheckboxesActionsList = DataList.builder()
            .id("dl-checkboxes-actions").ariaLabel("Data list")
            .item(DataList.item(DataListCell.title("Row with checkbox"), "Status: active", "Updated 2 hours ago")
                    .checkbox("dl-ca-1-check")
                    .action(TableAction.secondary("Action")))
            .item(DataList.item(DataListCell.title("Second row"), "Status: paused", "Updated yesterday")
                    .checkbox("dl-ca-2-check")
                    .action(TableAction.secondary("Action")))
            .item(DataList.item(DataListCell.title("Responsive actions row"),
                    "Buttons at the lg breakpoint and up", "A kebab menu below it")
                    .checkbox("dl-ca-3-check")
                    .responsiveActions("Row actions",
                            TableAction.primary("Primary"), TableAction.secondary("Secondary")))
            .build();

    public static DataList demoClickableRowsList = DataList.builder()
            .id("dl-clickable-rows").ariaLabel("Data list").clickable("1")
            .item(DataList.item(DataListCell.title("First row"), "Click or press Enter to select").key("1"))
            .item(DataList.item(DataListCell.title("Second row"), "The selected row carries pf-m-selected").key("2"))
            .build();

    public static DataList demoClickableExpandableList = DataList.builder()
            .id("dl-clickable-expandable").ariaLabel("Clickable expandable data list").clickable("1")
            .item(DataList.item(DataListCell.title("First row"), "Click selects; the toggle expands").key("1")
                    .expandsTo("Expanded details for the first row.").expanded())
            .item(DataList.item(DataListCell.title("Second row"), "The selected row carries pf-m-selected").key("2")
                    .expandsTo("Expanded details for the second row."))
            .build();

    public static DataList demoExpandableList = DataList.builder()
            .id("dl-expandable").ariaLabel("Data list")
            .item(DataList.item(DataListCell.title("Expandable row 1"), "Toggle reveals details")
                    .expandsTo("Expanded details for Expandable row 1.").expanded())
            .item(DataList.item(DataListCell.title("Expandable row 2"), "Collapsed by default")
                    .expandsTo("Expanded details for Expandable row 2."))
            .build();

    public static DataList demoCompactExpandableList = DataList.builder()
            .id("dl-compact-expandable").ariaLabel("Compact expandable data list").compact()
            .item(DataList.item(DataListCell.title("Compact row 1"), "Starts expanded")
                    .expandsTo("Expanded details for Compact row 1.").expanded())
            .item(DataList.item(DataListCell.title("Compact row 2"), "Collapsed by default")
                    .expandsTo("Expanded details for Compact row 2."))
            .build();

    public static DataList demoMixedExpandableList = DataList.builder()
            .id("dl-mixed-expandable").ariaLabel("Data list")
            .item(DataList.item(DataListCell.title("Expandable row"), "Has a toggle and details")
                    .expandsTo("Expanded details for the expandable row."))
            .item(DataList.item(DataListCell.title("Plain row"), "No toggle, no expandable content"))
            .build();

    public static DataList demoNestedExpandableList = DataList.builder()
            .id("dl-nested-expandable").ariaLabel("Nested expandable data list")
            .item(DataList.item(
                    DataListCell.icon("fa:code-branch"),
                    DataListCell.title("Outer row 1").description("Contains a nested data list"),
                    "Secondary content")
                    .expandsToList(nestedInnerList()).expanded())
            .item(DataList.item(
                    DataListCell.icon("fa:code-branch"),
                    DataListCell.title("Outer row 2"),
                    "Plain expandable details")
                    .expandsTo("Outer row 2 expanded content."))
            .build();

    private static DataList nestedInnerList() {
        return DataList.builder()
                .id("dl-nested-expandable-inner").ariaLabel("Nested data list")
                .item(DataList.item("Nested row 1").expandsTo("Nested row 1 expanded content.").expanded())
                .item(DataList.item("Nested row 2").expandsTo("Nested row 2 expanded content."))
                .item(DataList.item("Nested row 3").expandsTo("Nested row 3 expanded content."))
                .build();
    }
}
