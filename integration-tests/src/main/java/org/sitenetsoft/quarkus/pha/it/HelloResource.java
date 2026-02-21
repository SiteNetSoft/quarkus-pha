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
    private static final Map<String, String> IMPLEMENTED = Map.ofEntries(
        Map.entry("about-modal",      "/components/about-modal"),
        Map.entry("accordion",        "/components/accordion"),
        Map.entry("action-list",      "/components/action-list"),
        Map.entry("alert",            "/components/alert"),
        Map.entry("avatar",           "/components/avatar"),
        Map.entry("back-to-top",      "/components/back-to-top"),
        Map.entry("backdrop",         "/components/backdrop"),
        Map.entry("background-image", "/components/background-image"),
        Map.entry("badge",            "/components/badge"),
        Map.entry("banner",           "/components/banner"),
        Map.entry("brand",            "/components/brand"),
        Map.entry("breadcrumb",       "/components/breadcrumb"),
        Map.entry("button",           "/components/button"),
        Map.entry("calendar-month",   "/components/calendar-month"),
        Map.entry("card",             "/components/card"),
        Map.entry("checkbox",         "/components/checkbox"),
        Map.entry("chip",             "/components/chip"),
        Map.entry("clipboard-copy",   "/components/clipboard-copy"),
        Map.entry("code-block",       "/components/code-block"),
        Map.entry("code-editor",      "/components/code-editor"),
        Map.entry("content",          "/components/content"),
        Map.entry("context-selector", "/components/context-selector"),
        Map.entry("custom-menus",     "/components/custom-menus"),
        Map.entry("divider",          "/components/divider"),
        Map.entry("empty-state",      "/components/empty-state"),
        Map.entry("expandable-section", "/components/expandable-section"),
        Map.entry("helper-text",      "/components/helper-text"),
        Map.entry("hint",             "/components/hint"),
        Map.entry("icon",             "/components/icon"),
        Map.entry("label",            "/components/label"),
        Map.entry("list",             "/components/list"),
        Map.entry("panel",            "/components/panel"),
        Map.entry("progress",         "/components/progress"),
        Map.entry("skeleton",         "/components/skeleton"),
        Map.entry("spinner",          "/components/spinner"),
        Map.entry("timestamp",        "/components/timestamp"),
        Map.entry("title",            "/components/title"),
        Map.entry("truncate",         "/components/truncate"),
        Map.entry("data-list",        "/components/data-list"),
        Map.entry("description-list", "/components/description-list"),
        Map.entry("form",             "/components/form"),
        Map.entry("form-control",     "/components/form-control"),
        Map.entry("form-select",      "/components/form-select"),
        Map.entry("number-input",     "/components/number-input"),
        Map.entry("radio",            "/components/radio"),
        Map.entry("switch",           "/components/switch"),
        Map.entry("text-area",        "/components/text-area"),
        Map.entry("text-input",       "/components/text-input"),
        Map.entry("text-input-group", "/components/text-input-group"),
        Map.entry("toggle-group",     "/components/toggle-group"),
        Map.entry("simple-list",      "/components/simple-list"),
        Map.entry("simple-file-upload", "/components/simple-file-upload"),
        Map.entry("tile",             "/components/tile"),
        Map.entry("date-picker",      "/components/date-picker"),
        Map.entry("dropdown",         "/components/dropdown"),
        Map.entry("drawer",           "/components/drawer"),
        Map.entry("inline-edit",      "/components/inline-edit"),
        Map.entry("input-group",      "/components/input-group"),
        Map.entry("jump-links",       "/components/jump-links"),
        Map.entry("menu",             "/components/menu"),
        Map.entry("menu-toggle",      "/components/menu-toggle"),
        Map.entry("modal",            "/components/modal"),
        Map.entry("notification-badge", "/components/notification-badge"),
        Map.entry("notification-drawer", "/components/notification-drawer"),
        Map.entry("overflow-menu",    "/components/overflow-menu"),
        Map.entry("popover",          "/components/popover"),
        Map.entry("tooltip",          "/components/tooltip"),
        Map.entry("progress-stepper", "/components/progress-stepper"),
        Map.entry("login-page",      "/components/login-page"),
        Map.entry("masthead",        "/components/masthead"),
        Map.entry("navigation",      "/components/navigation"),
        Map.entry("page",            "/components/page"),
        Map.entry("pagination",      "/components/pagination"),
        Map.entry("search-input",    "/components/search-input"),
        Map.entry("select",          "/components/select"),
        Map.entry("sidebar",         "/components/sidebar"),
        Map.entry("skip-to-content", "/components/skip-to-content"),
        Map.entry("slider",          "/components/slider"),
        Map.entry("tabs",            "/components/tabs"),
        Map.entry("toolbar",         "/components/toolbar"),
        Map.entry("tree-view",       "/components/tree-view"),
        Map.entry("wizard",          "/components/wizard"),
        Map.entry("application-launcher", "/components/application-launcher"),
        Map.entry("date-and-time-picker", "/components/date-and-time-picker"),
        Map.entry("drag-and-drop",   "/components/drag-and-drop"),
        Map.entry("dual-list-selector", "/components/dual-list-selector"),
        Map.entry("multiple-file-upload", "/components/multiple-file-upload"),
        Map.entry("options-menu",    "/components/options-menu"),
        Map.entry("password-generator", "/components/password-generator"),
        Map.entry("password-strength", "/components/password-strength"),
        Map.entry("table",           "/components/table"),
        Map.entry("time-picker",     "/components/time-picker")
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
