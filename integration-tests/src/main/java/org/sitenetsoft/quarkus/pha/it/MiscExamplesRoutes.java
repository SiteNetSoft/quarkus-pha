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
        m.put("timestamp",             Set.of("basic", "inline", "with-tooltip"));
        m.put("truncate",              Set.of("end", "start", "middle"));
        m.put("tooltip",               Set.of("top", "bottom", "left-right"));
        m.put("switch",                Set.of("basic", "checked", "disabled", "reversed"));
        m.put("toggle-group",          Set.of("single-select", "multi-select", "with-icons"));
        m.put("slider",                Set.of("basic", "custom-range", "disabled"));
        m.put("text-input-group",      Set.of("basic", "with-icon", "disabled"));
        m.put("search-input",          Set.of("basic", "with-clear"));
        m.put("jump-links",            Set.of("horizontal", "vertical", "centered"));
        m.put("simple-list",           Set.of("basic", "grouped"));
        m.put("alert",                 Set.of("variants", "with-description", "closable", "inline"));
        m.put("clipboard-copy",        Set.of("basic", "readonly", "expandable", "inline"));
        m.put("card",                  Set.of("basic", "compact", "flat"));
        m.put("hint",                  Set.of("basic", "with-content"));
        m.put("expandable-section",    Set.of("collapsed", "expanded"));
        m.put("number-input",          Set.of("basic", "bounded", "with-unit", "disabled"));
        m.put("description-list",      Set.of("basic", "fill-columns"));
        m.put("skip-to-content",       Set.of("basic", "custom-text"));
        m.put("empty-state",           Set.of("basic", "no-icon", "with-actions"));
        m.put("pagination",            Set.of("basic", "compact"));
        m.put("code-block",            Set.of("basic", "with-copy"));
        m.put("progress-stepper",      Set.of("horizontal", "vertical", "compact"));
        m.put("tabs",                  Set.of("basic", "box", "vertical"));
        m.put("popover",               Set.of("basic", "danger"));
        m.put("masthead",              Set.of("basic", "display-stack"));
        m.put("sidebar",               Set.of("basic", "panel-right"));
        m.put("data-list",             Set.of("basic", "compact"));
        m.put("inline-edit",           Set.of("basic"));
        m.put("simple-file-upload",    Set.of("basic", "disabled"));
        m.put("overflow-menu",         Set.of("basic"));
        m.put("drag-and-drop",         Set.of("basic"));
        m.put("login-page",            Set.of("basic"));
        m.put("notification-drawer",   Set.of("basic"));
        m.put("drawer",                Set.of("basic", "static"));
        m.put("toolbar",               Set.of("basic"));
        m.put("modal",                 Set.of("basic", "sizes"));
        m.put("multiple-file-upload",  Set.of("basic", "horizontal"));
        m.put("date-picker",           Set.of("basic", "with-value", "disabled"));
        m.put("menu",                  Set.of("basic", "with-descriptions"));
        m.put("menu-toggle",           Set.of("basic", "plain", "primary", "expanded", "disabled"));
        m.put("notification-badge",    Set.of("read", "unread", "attention"));
        m.put("page",                  Set.of("basic"));
        m.put("context-selector",      Set.of("basic"));
        m.put("dual-list-selector",    Set.of("basic"));
        m.put("calendar-month",        Set.of("basic"));
        m.put("dropdown",              Set.of("basic", "plain-kebab"));
        m.put("select",                Set.of("single", "checkboxes"));
        m.put("application-launcher",  Set.of("basic"));
        m.put("options-menu",          Set.of("basic"));
        m.put("custom-menus",          Set.of("basic"));
        m.put("password-generator",    Set.of("basic"));
        m.put("password-strength",     Set.of("basic"));
        m.put("date-and-time-picker",  Set.of("basic"));
        m.put("time-picker",           Set.of("basic"));
        m.put("navigation",            Set.of("vertical", "grouped"));
        EXAMPLES = Map.copyOf(m);
    }

    private static final String NAME_REGEX =
        "timestamp|truncate|tooltip|switch|toggle-group|slider|text-input-group|search-input|" +
        "jump-links|simple-list|alert|clipboard-copy|card|hint|expandable-section|number-input|" +
        "description-list|skip-to-content|empty-state|pagination|code-block|progress-stepper|tabs|popover|" +
        "masthead|sidebar|data-list|inline-edit|simple-file-upload|overflow-menu|drag-and-drop|login-page|" +
        "notification-drawer|drawer|toolbar|modal|multiple-file-upload|date-picker|menu|menu-toggle|" +
        "notification-badge|page|context-selector|dual-list-selector|calendar-month|dropdown|select|" +
        "application-launcher|options-menu|custom-menus|password-generator|password-strength|" +
        "date-and-time-picker|time-picker|navigation";

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
