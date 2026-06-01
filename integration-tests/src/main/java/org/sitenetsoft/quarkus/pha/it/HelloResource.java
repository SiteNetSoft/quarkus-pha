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

    /**
     * Components without a homepage thumbnail in /web/images/components/.
     * Listed here so the template renders a placeholder instead of an
     * &lt;img&gt; that would 404. Drop an id from this set once a real
     * thumbnail PNG is added.
     */
    private static final Set<String> NO_THUMBNAIL = Set.of(
        "catalog-item-header",
        "catalog-tile",
        "chart",
        "data-view-overview",
        "data-view-toolbar",
        "data-view-table",
        "document-editor",
        "filter-side-panel",
        "log-viewer",
        "map",
        "properties-side-panel",
        "rectangle-selection",
        "rich-text-editor",
        "ripple",
        "user-feedback",
        "vertical-tabs",
        "video-player"
    );

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
        Map.entry("ripple",           "/components/ripple"),
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
        Map.entry("time-picker",     "/components/time-picker"),
        Map.entry("rectangle-selection", "/components/rectangle-selection"),
        Map.entry("map",                "/components/map"),
        Map.entry("document-editor",   "/components/document-editor"),
        Map.entry("chart",             "/components/chart"),
        Map.entry("topology",          "/components/topology"),
        Map.entry("live-search",       "/components/live-search"),
        Map.entry("infinite-scroll",   "/components/infinite-scroll"),
        Map.entry("click-to-load",     "/components/click-to-load"),
        Map.entry("lazy-modal",        "/components/lazy-modal"),
        Map.entry("video-player",      "/components/video-player"),
        Map.entry("rich-text-editor",  "/components/rich-text-editor"),
        Map.entry("log-viewer",            "/extensions/log-viewer"),
        Map.entry("user-feedback",         "/extensions/user-feedback"),
        Map.entry("catalog-item-header",   "/extensions/catalog-view/catalog-item-header"),
        Map.entry("catalog-tile",          "/extensions/catalog-view/catalog-tile"),
        Map.entry("filter-side-panel",     "/extensions/catalog-view/filter-side-panel"),
        Map.entry("properties-side-panel", "/extensions/catalog-view/properties-side-panel"),
        Map.entry("vertical-tabs",         "/extensions/catalog-view/vertical-tabs"),
        Map.entry("data-view-overview",    "/extensions/data-view/overview"),
        Map.entry("data-view-toolbar",     "/extensions/data-view/toolbar"),
        Map.entry("data-view-table",       "/extensions/data-view/table")
    );

    /** Components tagged with a footer label. */
    private static final Map<String, String> LABELS = Map.ofEntries(
        Map.entry("application-launcher", "Demo"),
        Map.entry("chip",                 "Deprecated"),
        Map.entry("context-selector",     "Demo"),
        Map.entry("custom-menus",         "Demo"),
        Map.entry("date-and-time-picker", "Demo"),
        Map.entry("dropdown",             "Beta"),
        Map.entry("options-menu",         "Demo"),
        Map.entry("password-generator",   "Demo"),
        Map.entry("password-strength",    "Demo"),
        Map.entry("select",               "Beta"),
        Map.entry("rectangle-selection",  "Custom"),
        Map.entry("map",                "Custom"),
        Map.entry("document-editor",   "Custom"),
        Map.entry("chart",             "Custom"),
        Map.entry("topology",          "Custom"),
        Map.entry("live-search",       "HTMX"),
        Map.entry("infinite-scroll",   "HTMX"),
        Map.entry("click-to-load",     "HTMX"),
        Map.entry("lazy-modal",        "HTMX"),
        Map.entry("video-player",      "Custom"),
        Map.entry("rich-text-editor",  "Custom"),
        Map.entry("ripple",            "Custom"),
        Map.entry("log-viewer",            "Extension"),
        Map.entry("user-feedback",         "Extension"),
        Map.entry("catalog-item-header",   "Extension"),
        Map.entry("catalog-tile",          "Extension"),
        Map.entry("filter-side-panel",     "Extension"),
        Map.entry("properties-side-panel", "Extension"),
        Map.entry("vertical-tabs",         "Extension"),
        Map.entry("data-view-overview",    "Extension"),
        Map.entry("data-view-toolbar",     "Extension"),
        Map.entry("data-view-table",       "Extension")
    );

    /** Label text → PF label color modifier. */
    private static final Map<String, String> LABEL_COLORS = Map.of(
        "Demo",       "pf-m-purple",
        "Beta",       "pf-m-blue",
        "Deprecated", "",
        "Custom",     "pf-m-teal",
        "HTMX",       "pf-m-green",
        "Extension",  "pf-m-orange"
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
        {"catalog-item-header",  "Catalog item header"},
        {"catalog-tile",         "Catalog tile"},
        {"chart",                "Chart"},
        {"checkbox",             "Checkbox"},
        {"chip",                 "Chip"},
        {"click-to-load",        "Click to load"},
        {"clipboard-copy",       "Clipboard copy"},
        {"code-block",           "Code block"},
        {"code-editor",          "Code editor"},
        {"content",              "Content"},
        {"context-selector",     "Context selector"},
        {"custom-menus",         "Custom menus"},
        {"data-list",            "Data list"},
        {"data-view-overview",   "Data view overview"},
        {"data-view-toolbar",    "Data view toolbar"},
        {"data-view-table",      "Data view table"},
        {"date-and-time-picker", "Date and time picker"},
        {"date-picker",          "Date picker"},
        {"description-list",     "Description list"},
        {"divider",              "Divider"},
        {"document-editor",      "Document editor"},
        {"drag-and-drop",        "Drag and drop"},
        {"drawer",               "Drawer"},
        {"dropdown",             "Dropdown"},
        {"dual-list-selector",   "Dual list selector"},
        {"empty-state",          "Empty state"},
        {"expandable-section",   "Expandable section"},
        {"filter-side-panel",    "Filter side panel"},
        {"form",                 "Form"},
        {"form-control",         "Form control"},
        {"form-select",          "Form select"},
        {"helper-text",          "Helper text"},
        {"hint",                 "Hint"},
        {"icon",                 "Icon"},
        {"infinite-scroll",      "Infinite scroll"},
        {"inline-edit",          "Inline edit"},
        {"input-group",          "Input group"},
        {"jump-links",           "Jump links"},
        {"label",                "Label"},
        {"lazy-modal",           "Lazy modal"},
        {"list",                 "List"},
        {"live-search",          "Live search"},
        {"log-viewer",           "Log viewer"},
        {"login-page",           "Login page"},
        {"map",                  "Map"},
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
        {"properties-side-panel","Properties side panel"},
        {"radio",                "Radio"},
        {"rectangle-selection",  "Rectangle selection"},
        {"rich-text-editor",     "Rich text editor"},
        {"ripple",               "Ripple"},
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
        {"topology",             "Topology"},
        {"tree-view",            "Tree view"},
        {"truncate",             "Truncate"},
        {"user-feedback",        "User feedback"},
        {"vertical-tabs",        "Vertical tabs"},
        {"video-player",         "Video player"},
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
            if (!NO_THUMBNAIL.contains(id)) {
                comp.put("image", "/web/images/components/" + id + ".png");
            }
            comp.put("href", IMPLEMENTED.getOrDefault(id, "#"));

            String labelText = LABELS.get(id);
            if (labelText == null && DEPRECATED.contains(id)) {
                labelText = "Deprecated";
            }
            comp.put("label", labelText != null ? labelText : "");
            comp.put("labelColor", labelText != null ? LABEL_COLORS.getOrDefault(labelText, "") : "");

            components.add(comp);
        }

        return hello
                     .data("components", components)
                     .data("componentCount", components.size());
    }
}
