package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Per-example endpoints for small / medium components that don't already have
 * their own *ExamplesRoutes class. The regex on {@code name} scopes the routes —
 * adding a new component means listing it in both EXAMPLES and the regex.
 */
@Path("/components")
public class MiscExamplesRoutes {

    private static final Map<String, Set<String>> EXAMPLES;
    static {
        Map<String, Set<String>> m = new HashMap<>();
        m.put("timestamp",             Set.of("basic", "basic-formats", "custom-format", "custom-content", "inline", "with-tooltip"));
        m.put("hero",                  Set.of("basic", "glass"));
        m.put("input-group",           Set.of("basic", "with-textarea", "with-dropdown", "with-popover",
                                               "multiple-siblings"));
        m.put("compass",               Set.of("basic", "alternate-footer", "docked-nav", "main-header-structure"));
        m.put("tile",                  Set.of("basic", "with-subtext", "with-icon", "stacked-icon",
                                               "large-icons", "long-subtext", "single-selection",
                                               "multiple-selection"));
        m.put("truncate",              Set.of("end", "start", "middle", "custom-tooltip-position", "max-chars", "with-links"));
        m.put("tooltip",               Set.of("basic", "positions", "on-icon", "long-content"));
        m.put("switch",                Set.of("basic", "checked", "disabled", "reversed", "without-label"));
        m.put("toggle-group",          Set.of("single-select", "multi-select", "with-icons", "text-and-icons", "compact", "full-width"));
        m.put("slider",                Set.of("basic", "continuous", "value-input", "actions", "custom-range", "disabled"));
        m.put("text-input-group",      Set.of("basic", "with-icon", "disabled", "plain", "autocomplete-hint", "filters", "filters-expanded", "with-status"));
        m.put("search-input",          Set.of("basic", "with-clear", "no-match", "result-count", "navigable-options",
                                               "with-submit", "expandable", "advanced", "advanced-expanded",
                                               "autocomplete", "autocomplete-hint"));
        m.put("jump-links",            Set.of("horizontal", "vertical", "centered", "with-label", "vertical-with-label", "expandable-vertical-subsection"));
        m.put("simple-list",           Set.of("basic", "links", "grouped", "selectable"));
        m.put("alert",                 Set.of("variants", "variations", "with-description", "closable", "inline",
                                               "with-actions", "timeout", "expandable", "truncated", "custom-icons",
                                               "plain-inline", "static-live-region", "alert-group-static",
                                               "alert-group-toast", "alert-group-toast-overflow", "dynamic-groups"));
        m.put("clipboard-copy",        Set.of("basic", "readonly", "expandable", "read-only-expanded",
                                               "read-only-expanded-by-default", "expanded-with-array", "json-pre",
                                               "inline-compact", "inline", "inline-compact-with-additional-action",
                                               "inline-compact-truncation"));
        m.put("card",                  Set.of("basic", "subtitle", "subtitle-actions", "secondary", "compact", "flat",
                                               "modifiers", "header-images-actions", "title-inline-images-actions",
                                               "header-without-title", "header-wraps", "heading-element",
                                               "multiple-body-sections", "body-section-fills", "selectable",
                                               "single-selectable", "actionable", "actionable-selectable",
                                               "expandable", "expandable-with-icon", "with-dividers",
                                               "single-selectable-tiles", "multi-selectable-tiles"));
        m.put("hint",                  Set.of("basic", "without-title", "with-content"));
        m.put("expandable-section",    Set.of("collapsed", "expanded", "dynamic-toggle-text", "detached",
                                               "disclosure", "indented", "custom-toggle", "heading-semantics",
                                               "truncate-expansion"));
        m.put("number-input",          Set.of("basic", "with-unit", "bounded", "disabled", "with-status",
                                               "varying-sizes", "custom-step", "custom-step-threshold"));
        m.put("description-list",      Set.of("basic", "term-help-text", "default-2-col", "default-3-col-on-lg",
                                               "horizontal", "horizontal-custom-term-width", "horizontal-2-col",
                                               "horizontal-3-col-on-lg", "compact", "compact-horizontal",
                                               "fluid-horizontal", "fill-columns", "large-display-size",
                                               "default-responsive-columns", "horizontal-responsive-columns",
                                               "responsive-hori-vert-group", "default-auto-column-width",
                                               "horizontal-auto-column-width", "default-inline-grid", "with-card",
                                               "large-display-size-and-card", "display-size-card-3-col-lg",
                                               "display-size-card-horizontal-term-width", "auto-fit",
                                               "auto-fit-min-width", "auto-fit-min-width-responsive",
                                               "icons-on-terms"));
        m.put("skip-to-content",       Set.of("basic", "custom-text"));
        m.put("empty-state",           Set.of("basic", "extra-small", "small", "large", "extra-large", "success", "spinner", "no-match", "no-icon", "with-actions"));
        m.put("pagination",            Set.of("basic", "top", "bottom", "compact", "indeterminate", "disabled",
                                               "no-items", "one-page", "offset", "inset", "sticky",
                                               "dynamic-sticky"));
        m.put("code-block",            Set.of("basic", "with-copy"));
        m.put("progress-stepper",      Set.of("horizontal", "vertical", "compact", "with-alignment", "with-issue",
                                               "with-failure", "custom-icons", "help-popover"));
        m.put("tabs",                  Set.of("basic", "box", "box-secondary", "box-vertical", "vertical",
                                               "vertical-expandable", "vertical-expandable-responsive",
                                               "overflow", "horizontal-overflow", "box-overflow",
                                               "inset", "box-inset", "page-insets", "icons-text",
                                               "subtabs", "box-subtabs", "filled", "filled-with-icons",
                                               "filled-box", "filled-box-with-icons",
                                               "nav-tabs", "nav-subtabs", "site-nav",
                                               "content-body-padding", "tab-content", "tab-content-secondary",
                                               "dynamic", "help", "close", "help-and-close",
                                               "animate-default", "animate-subtabs", "animate-filled",
                                               "animate-vertical"));
        m.put("popover",               Set.of("basic", "hoverable", "close-from-content", "no-header-footer",
                                               "width-auto", "advanced", "icon-in-title", "alert-variants",
                                               "danger"));
        m.put("masthead",              Set.of("basic", "mixed-content", "display-inline", "display-stack",
                                               "display-stack-inline-responsive", "insets", "custom-logo"));
        m.put("sidebar",               Set.of("basic", "stack", "panel-right", "panel-right-gutter",
                                               "sticky-panel", "static-panel", "responsive-panel", "border",
                                               "padding-panel", "padding-content"));
        m.put("data-list",             Set.of("basic", "compact", "plain", "checkboxes-actions", "actions",
                                               "expandable", "mixed-expandable", "width-modifiers", "clickable-rows",
                                               "controlling-text", "sm-grid-breakpoint"));
        m.put("inline-edit",           Set.of("basic", "with-label", "multiple", "validated"));
        m.put("simple-file-upload",    Set.of("basic", "simple-text-file", "with-helper-text", "text-with-edits",
                                               "text-with-restrictions", "custom-file-preview", "custom-upload",
                                               "disabled"));
        m.put("overflow-menu",         Set.of("basic", "vertical", "group-types", "multiple-groups", "persistent",
                                               "breakpoint-container-width", "breakpoint-container-height"));
        m.put("drag-and-drop",         Set.of("basic", "multiple-zones", "data-list", "dual-list"));
        m.put("login-page",            Set.of("basic", "show-hide-password", "header-utilities"));
        m.put("notification-drawer",   Set.of("basic", "groups", "lightweight"));
        m.put("drawer",                Set.of("basic", "panel-end", "panel-start", "panel-bottom", "basic-inline",
                                               "inline-panel-end", "inline-panel-start", "stacked-content-body",
                                               "modified-content-padding", "modified-panel-padding",
                                               "additional-section", "static", "breakpoint", "resizable-end",
                                               "resizable-start", "resizable-bottom", "resizable-inline",
                                               "secondary-background", "focus-trap", "pill", "pill-inline"));
        m.put("toolbar",               Set.of("basic", "items", "insets", "vertical", "sticky", "dynamic-sticky",
                                               "groups", "color-variants", "toggle-groups", "with-filters", "stacked",
                                               "content-wrap", "group-spacers", "item-spacers", "no-padding",
                                               "width-control", "filter-group", "action-group", "action-group-plain",
                                               "action-group-inline", "label-group", "custom-label-group-content"));
        m.put("modal",                 Set.of("basic", "scrollable", "with-description", "top-aligned", "sizes",
                                               "custom-width", "custom-header", "no-header-footer", "title-icon",
                                               "custom-title-icon", "with-dropdown", "with-help", "with-form"));
        m.put("multiple-file-upload",  Set.of("basic", "horizontal"));
        m.put("date-picker",           Set.of("basic", "required", "american-format", "helper-text", "min-max",
                                               "french", "with-value", "disabled"));
        m.put("menu",                  Set.of("basic", "with-icons", "with-actions", "with-links", "with-descriptions",
                                               "item-checkbox", "footer", "separated-items", "titled-groups",
                                               "favorites", "filtering-search", "option-single-select",
                                               "option-multi-select", "scrollable", "scrollable-custom-height",
                                               "view-more", "with-drilldown"));
        m.put("menu-toggle",           Set.of("basic", "expanded", "small", "disabled", "badge", "settings",
                                               "custom-icon", "avatar-text", "variant-styles", "plain", "primary",
                                               "plain-circle", "plain-text-label", "split-checkbox",
                                               "split-checkbox-text", "split-checkbox-toggle-text", "split-action",
                                               "full-height", "full-width", "in-form", "typeahead", "status",
                                               "placeholder"));
        m.put("notification-badge",    Set.of("read", "unread", "attention"));
        m.put("page",                  Set.of("basic", "vertical-nav", "multiple-sidebar-body", "horizontal-nav",
                                               "uncontrolled-nav", "filled-sections", "main-section-padding",
                                               "main-section-variations", "group-section", "centered-section",
                                               "plain-sections", "dynamic-sticky-section"));
        m.put("context-selector",      Set.of("basic"));
        m.put("dual-list-selector",    Set.of("basic", "with-tooltips", "with-search", "complex-actions"));
        m.put("calendar-month",        Set.of("basic", "date-range"));
        m.put("dropdown",              Set.of("basic", "plain-kebab", "with-groups", "with-descriptions", "split-checkbox"));
        m.put("select",                Set.of("single", "option-variants", "grouped", "validation", "checkboxes",
                                               "typeahead", "typeahead-creatable", "multi-typeahead",
                                               "multi-typeahead-creatable", "multi-typeahead-checkbox", "view-more",
                                               "footer"));
        m.put("application-launcher",  Set.of("basic", "with-sections", "text-items", "with-external", "align-right"));
        m.put("infinite-scroll",       Set.of("basic"));
        m.put("options-menu",          Set.of("basic"));
        m.put("custom-menus",          Set.of("basic", "with-search", "flyout"));
        m.put("password-generator",    Set.of("basic"));
        m.put("password-strength",     Set.of("basic"));
        m.put("date-and-time-picker",  Set.of("basic"));
        m.put("time-picker",           Set.of("basic", "twelve-hour", "custom-delimiter", "min-max",
                                               "with-seconds", "twenty-four-with-seconds"));
        m.put("navigation",            Set.of("vertical", "grouped", "expandable", "expandable-third-level",
                                               "mixed", "horizontal", "horizontal-subnav", "icons"));
        EXAMPLES = Map.copyOf(m);
    }

    private static final String NAME_REGEX =
        "timestamp|truncate|tooltip|switch|toggle-group|slider|text-input-group|search-input|" +
        "jump-links|simple-list|alert|clipboard-copy|card|hint|expandable-section|number-input|" +
        "description-list|skip-to-content|empty-state|pagination|code-block|progress-stepper|tabs|popover|" +
        "masthead|sidebar|data-list|inline-edit|simple-file-upload|overflow-menu|drag-and-drop|login-page|" +
        "notification-drawer|drawer|toolbar|modal|multiple-file-upload|date-picker|menu|menu-toggle|" +
        "notification-badge|page|context-selector|dual-list-selector|calendar-month|dropdown|select|" +
        "application-launcher|infinite-scroll|options-menu|custom-menus|password-generator|password-strength|" +
        "date-and-time-picker|time-picker|navigation|tile|hero|input-group|compass";

    @Inject
    Engine engine;

    @GET
    @Path("/{name:(" + NAME_REGEX + ")}/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        String resourcePath = "/templates/components/" + name + "/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + name + "/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/{name:(" + NAME_REGEX + ")}/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        Template inner = engine.getTemplate("components/" + name + "/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found: " + name + "/" + example);
        }
        Template standalone = engine.getTemplate("components/" + name + "/_standalone");
        if (standalone == null) {
            throw new NotFoundException("Standalone wrapper missing for: " + name);
        }
        String rendered = inner.instance().render();
        return standalone.instance()
            .data("name", name)
            .data("example", example)
            .data("content", rendered);
    }

    private void validate(String name, String example) {
        Set<String> allowed = EXAMPLES.get(name);
        if (allowed == null || !allowed.contains(example)) {
            throw new NotFoundException("Unknown misc example: " + name + "/" + example);
        }
    }
}
