package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Location;
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
import java.util.List;
import java.util.Map;

/**
 * Routing for the /patterns top-level bucket.
 *
 *   GET /patterns                         → landing card grid
 *   GET /patterns/{name}                  → shared demo page with 2 examples
 *   GET /patterns/{name}/{example}        → standalone fragment wrapped in layouts/base
 *   GET /patterns/{name}/source/{example} → raw Qute source as text/plain
 */
@Path("/patterns")
public class PatternsRoutes {

    private static final Map<String, String> TITLES = Map.ofEntries(
        Map.entry("card-view",          "Card view"),
        Map.entry("dashboard",          "Dashboard"),
        Map.entry("filters",            "Filters"),
        Map.entry("password-generator", "Password generator"),
        Map.entry("password-strength",  "Password strength"),
        Map.entry("primary-detail",     "Primary-detail"),
        Map.entry("right-to-left",      "Right-to-left")
    );

    private static final Map<String, String> INTROS = Map.ofEntries(
        Map.entry("card-view",          "Browseable card grid — projects, catalogue items, content tiles — usually with a toolbar above for search / sort / layout-toggle."),
        Map.entry("dashboard",          "Stat tiles on top, a chart in the middle, an activity table beneath. The shape every \"overview\" page tends to converge on."),
        Map.entry("filters",            "Attribute filtering in a toolbar. Two flavours — chip filters (visible selections, click ×  to remove) and an advanced builder (attribute + value pair, apply on click)."),
        Map.entry("password-generator", "How to drop the password-generator widget into a real flow — inside a \"create user\" modal, or side-by-side with a live strength meter."),
        Map.entry("password-strength",  "Score + criteria checklist next to a password input. Score in five bands; criteria flip green as the user types."),
        Map.entry("primary-detail",     "Master-detail. A list on the left selects what shows on the right — projects → metadata, services → endpoints table, anything."),
        Map.entry("right-to-left",      "PatternFly's CSS handles RTL out of the box: set <code class=\"ws-code\">dir=\"rtl\"</code> on the root and the components mirror automatically. The trick is keeping <em>data</em> (URLs, IDs, codes) LTR with nested <code class=\"ws-code\">dir=\"ltr\"</code>.")
    );

    private static final Map<String, String> USAGE = Map.ofEntries(
        Map.entry("card-view",          "<p>Lean on the <code class=\"ws-code\">pf-v6-l-gallery</code> layout. The toolbar above is independently driven — server-side, paired with HTMX swaps for filter / sort changes.</p>"),
        Map.entry("dashboard",          "<p>Build the page as one <code class=\"ws-code\">pf-v6-l-stack</code> with gutter. Each row is either a gallery of compact stat cards, a single card hosting a chart, or a full-width card hosting a table.</p>"),
        Map.entry("filters",            "<p>Chip filters are best when the user needs to see what's applied at all times. The advanced row works better when there are many attributes — the dropdown keeps the toolbar narrow.</p><p>Either way, the actual filtering happens on the server — clicking <strong>Apply</strong> or <strong>×</strong> dispatches an HTMX <code class=\"ws-code\">hx-get</code> that re-renders the list below.</p>"),
        Map.entry("password-generator", "<p>The widget is small enough to live anywhere — inside a modal, in a settings page, in a wizard step. Generate with <code class=\"ws-code\">crypto.getRandomValues</code> client-side; never let the password touch the server before the user submits the form.</p>"),
        Map.entry("password-strength",  "<p>Score on submit (server-side) AND while typing (client-side). The client meter is UX; the server is the rule enforcer. Use the same scoring function in both places.</p>"),
        Map.entry("primary-detail",     "<p>For wide viewports show list + detail side-by-side; on narrow screens collapse to a stack with the list on top and the detail under it. <strong>Selection</strong> drives the right pane via HTMX — clicking a list item does <code class=\"ws-code\">hx-get</code> targeting the detail panel.</p>"),
        Map.entry("right-to-left",      "<p>Set <code class=\"ws-code\">dir=\"rtl\"</code> on <code class=\"ws-code\">&lt;html&gt;</code> (or on a wrapping element) and PatternFly's stylesheet does the rest. Keep technical strings — URLs, IDs, version numbers, code blocks — wrapped in <code class=\"ws-code\">dir=\"ltr\"</code> so they don't reverse.</p>")
    );

    /** Examples available per pattern. */
    private static final Map<String, List<String>> EXAMPLES = Map.ofEntries(
        Map.entry("card-view",          List.of("basic", "with-toolbar")),
        Map.entry("dashboard",          List.of("overview", "dense")),
        Map.entry("filters",            List.of("chip-filters", "advanced")),
        Map.entry("password-generator", List.of("in-modal", "with-strength")),
        Map.entry("password-strength",  List.of("basic", "with-tooltip")),
        Map.entry("primary-detail",     List.of("list-detail", "data-list-detail")),
        Map.entry("right-to-left",      List.of("form-rtl", "dashboard-rtl"))
    );

    @Inject
    Engine engine;

    @Location("patterns/index")
    @Inject
    Template landingPage;

    @Location("patterns/_demo-page")
    @Inject
    Template demoPage;

    @Location("patterns/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance landing() {
        return landingPage.instance();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance demo(@PathParam("name") String name) {
        String title = TITLES.get(name);
        if (title == null) {
            throw new NotFoundException("Unknown pattern: " + name);
        }
        return demoPage.instance()
            .data("type", name)
            .data("title", title)
            .data("intro", INTROS.get(name))
            .data("usage", USAGE.get(name));
    }

    @GET
    @Path("/{name}/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        String resourcePath = "/templates/patterns/" + name + "/" + example + ".html";
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
    @Path("/{name}/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        Template inner = engine.getTemplate("patterns/" + name + "/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + name + "/" + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("name", name)
            .data("example", example)
            .data("content", rendered);
    }

    private void validate(String name, String example) {
        List<String> allowed = EXAMPLES.get(name);
        if (allowed == null || !allowed.contains(example)) {
            throw new NotFoundException("Unknown pattern example: " + name + "/" + example);
        }
    }
}
