package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/")
public class HelloResource {

    @Inject
    Template hello;

    /** Components that have a working demo page. */
    private static final Map<String, String> IMPLEMENTED = Map.of(
        "about-modal",  "/components/about-modal",
        "accordion",    "/components/accordion",
        "action-list",  "/components/action-list"
    );

    /** Components tagged with a footer label. */
    private static final Map<String, String> LABELS = Map.of(
        "application-launcher", "Demo",
        "chip",                 "Deprecated",
        "context-selector",     "Demo",
        "custom-menus",         "Demo",
        "date-and-time-picker", "Demo",
        "dropdown",             "Beta",
        "options-menu",         "Demo",
        "password-generator",   "Demo",
        "password-strength",    "Demo",
        "select",               "Beta"
    );

    /** Label text → PF label color modifier. */
    private static final Map<String, String> LABEL_COLORS = Map.of(
        "Demo",       "pf-m-purple",
        "Beta",       "pf-m-blue",
        "Deprecated", ""
    );

    /** Deprecated components also need "Tile". */
    private static final Set<String> DEPRECATED = Set.of("chip", "tile");

    private static final String[][] COMPONENT_DATA = {
        {"about-modal",          "About modal"},
        {"accordion",            "Accordion"},
        {"action-list",          "Action list"},
        {"alert",                "Alert"},
        {"application-launcher", "Application launcher"},
        {"avatar",               "Avatar"},
        {"back-to-top",          "Back to top"},
        {"backdrop",             "Backdrop"},
        {"background-image",     "Background image"},
        {"badge",                "Badge"},
        {"banner",               "Banner"},
        {"brand",                "Brand"},
        {"breadcrumb",           "Breadcrumb"},
        {"button",               "Button"},
        {"calendar-month",       "Calendar month"},
        {"card",                 "Card"},
        {"checkbox",             "Checkbox"},
        {"chip",                 "Chip"},
        {"clipboard-copy",       "Clipboard copy"},
        {"code-block",           "Code block"},
        {"code-editor",          "Code editor"},
        {"content",              "Content"},
        {"context-selector",     "Context selector"},
        {"custom-menus",         "Custom menus"},
        {"data-list",            "Data list"},
        {"date-and-time-picker", "Date and time picker"},
        {"date-picker",          "Date picker"},
        {"description-list",     "Description list"},
        {"divider",              "Divider"},
        {"drag-and-drop",        "Drag and drop"},
        {"drawer",               "Drawer"},
        {"dropdown",             "Dropdown"},
        {"dual-list-selector",   "Dual list selector"},
        {"empty-state",          "Empty state"},
        {"expandable-section",   "Expandable section"},
        {"form",                 "Form"},
        {"form-control",         "Form control"},
        {"form-select",          "Form select"},
        {"helper-text",          "Helper text"},
        {"hint",                 "Hint"},
        {"icon",                 "Icon"},
        {"inline-edit",          "Inline edit"},
        {"input-group",          "Input group"},
        {"jump-links",           "Jump links"},
        {"label",                "Label"},
        {"list",                 "List"},
        {"login-page",           "Login page"},
        {"masthead",             "Masthead"},
        {"menu",                 "Menu"},
        {"menu-toggle",          "Menu toggle"},
        {"modal",                "Modal"},
        {"multiple-file-upload", "Multiple file upload"},
        {"navigation",           "Navigation"},
        {"notification-badge",   "Notification badge"},
        {"notification-drawer",  "Notification drawer"},
        {"number-input",         "Number input"},
        {"options-menu",         "Options menu"},
        {"overflow-menu",        "Overflow menu"},
        {"page",                 "Page"},
        {"pagination",           "Pagination"},
        {"panel",                "Panel"},
        {"password-generator",   "Password generator"},
        {"password-strength",    "Password strength"},
        {"popover",              "Popover"},
        {"progress",             "Progress"},
        {"progress-stepper",     "Progress stepper"},
        {"radio",                "Radio"},
        {"search-input",         "Search input"},
        {"select",               "Select"},
        {"sidebar",              "Sidebar"},
        {"simple-file-upload",   "Simple file upload"},
        {"simple-list",          "Simple list"},
        {"skeleton",             "Skeleton"},
        {"skip-to-content",      "Skip to content"},
        {"slider",               "Slider"},
        {"spinner",              "Spinner"},
        {"switch",               "Switch"},
        {"table",                "Table"},
        {"tabs",                 "Tabs"},
        {"text-area",            "Text area"},
        {"text-input",           "Text input"},
        {"text-input-group",     "Text input group"},
        {"tile",                 "Tile"},
        {"time-picker",          "Time picker"},
        {"timestamp",            "Timestamp"},
        {"title",                "Title"},
        {"toggle-group",         "Toggle group"},
        {"toolbar",              "Toolbar"},
        {"tooltip",              "Tooltip"},
        {"tree-view",            "Tree view"},
        {"truncate",             "Truncate"},
        {"wizard",               "Wizard"},
    };

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        List<Map<String, String>> components = new ArrayList<>();

        for (String[] entry : COMPONENT_DATA) {
            String id = entry[0];
            String name = entry[1];

            Map<String, String> comp = new HashMap<>();
            comp.put("id", id);
            comp.put("name", name);
            comp.put("image", "/web/images/components/" + id + ".png");
            comp.put("href", IMPLEMENTED.getOrDefault(id, "#"));

            String labelText = LABELS.get(id);
            if (labelText == null && DEPRECATED.contains(id)) {
                labelText = "Deprecated";
            }
            comp.put("label", labelText != null ? labelText : "");
            comp.put("labelColor", labelText != null ? LABEL_COLORS.getOrDefault(labelText, "") : "");

            components.add(comp);
        }

        return hello.data("applicationName", "quarkus-pha")
                     .data("components", components)
                     .data("componentCount", components.size());
    }
}
