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
import java.util.Map;
import java.util.Set;

/**
 * Routing for the 13 chart-type demo pages.
 *
 *   GET /components/chart/{type}                  → shared demo page with Basic example
 *   GET /components/chart/{type}/{example}        → standalone wrapper around the example fragment
 *   GET /components/chart/{type}/source/{example} → raw Qute source as text/plain
 *
 * The shared demo template lives at templates/components/chart/_demo-page.html
 * and switches on {type} to include the right example. Per-type intro copy is
 * supplied here so the template only needs the type slug.
 */
@Path("/components/chart")
public class ChartExamplesRoutes {

    /** Slug → display title. */
    private static final Map<String, String> TITLES = Map.ofEntries(
        Map.entry("area",              "Area chart"),
        Map.entry("bar",               "Bar chart"),
        Map.entry("box-plot",          "Box plot chart"),
        Map.entry("bullet",            "Bullet chart"),
        Map.entry("donut",             "Donut chart"),
        Map.entry("donut-utilization", "Donut utilization chart"),
        Map.entry("line",              "Line chart"),
        Map.entry("pie",               "Pie chart"),
        Map.entry("sankey",            "Sankey chart"),
        Map.entry("scatter",           "Scatter chart"),
        Map.entry("sparkline",         "Sparkline chart"),
        Map.entry("stack",             "Stack chart"),
        Map.entry("threshold",         "Threshold chart")
    );

    /** Slug → intro paragraph (HTML allowed). */
    private static final Map<String, String> INTROS = Map.ofEntries(
        Map.entry("area",              "A filled line chart. Use when you want to emphasise the magnitude of change over time, or to stack contributing series under a total."),
        Map.entry("bar",               "Vertical bars comparing values across categories. Grouped bars handle a second dimension (e.g. year-over-year)."),
        Map.entry("box-plot",          "Distribution per category — min, Q1, median, Q3, max. Use when you want a single picture of spread and skew across several groups."),
        Map.entry("bullet",            "Single-value performance vs. a target, anchored against quantitative range bands (Critical / Warning / Good). Better than a gauge in small spaces."),
        Map.entry("donut",             "Pie with a hole — gives a centred slot for a label or total. Default radius pair is <code class=\"ws-code\">['45%', '70%']</code>."),
        Map.entry("donut-utilization", "Single-value gauge rendered as a donut. The centre label shows the utilisation percentage, the arc colour reflects the threshold band."),
        Map.entry("line",              "Continuous values over time. Use smooth lines for slowly-changing metrics; keep them straight if individual data points matter."),
        Map.entry("pie",               "Parts of a whole. Best with five or fewer categories — beyond that, switch to a bar chart."),
        Map.entry("sankey",            "Directional flow between sets of nodes. Node width = total throughput; link width = volume between two nodes."),
        Map.entry("scatter",           "One dot per observation, mapping two numeric variables. Use to spot correlation, clusters, and outliers."),
        Map.entry("sparkline",         "A tiny inline trend chart — no axes, no legend. Pair with a value in a card header or table cell."),
        Map.entry("stack",             "Stacked bars showing how the parts of a whole vary across categories. Use only when the total itself is meaningful."),
        Map.entry("threshold",         "Line chart with a coloured warning / critical band overlay so the eye lands on the breach window without needing a tooltip.")
    );

    /** Prose-only sub-items (chart docs). Each slug has a {slug}-doc.html body fragment. */
    private static final Map<String, String> DOC_TITLES = Map.ofEntries(
        Map.entry("colors",          "Colors for charts"),
        Map.entry("legends",         "Legends"),
        Map.entry("patterns",        "Patterns"),
        Map.entry("resize-observer", "Resize observer"),
        Map.entry("skeletons",       "Skeletons"),
        Map.entry("themes",          "Themes"),
        Map.entry("tooltips",        "Tooltips")
    );

    private static final Map<String, String> DOC_INTROS = Map.ofEntries(
        Map.entry("colors",          "How the PatternFly chart palette is structured and how to override it for semantic colour."),
        Map.entry("legends",         "Placement, orientation and click-to-toggle behaviour for the chart legend."),
        Map.entry("patterns",        "Hatch fills for accessibility-safe series differentiation when colour alone isn't enough."),
        Map.entry("resize-observer", "How <code class=\"ws-code\">phaChart</code> stays sized as the viewport (or the container) changes."),
        Map.entry("skeletons",       "Loading placeholders — the ECharts built-in spinner and a PatternFly-skeleton swap pattern."),
        Map.entry("themes",          "The default <code class=\"ws-code\">patternfly</code> ECharts theme and how to register your own."),
        Map.entry("tooltips",        "Tooltip <code class=\"ws-code\">trigger</code> modes (item vs. axis) and the gotcha with ECharts placeholder tokens inside Qute templates.")
    );

    private static final Set<String> EXAMPLES = Set.of("basic");

    @Inject
    Engine engine;

    @Location("components/chart")
    @Inject
    Template landingPage;

    @Location("components/chart/_demo-page")
    @Inject
    Template demoPage;

    @Location("components/chart/_doc-page")
    @Inject
    Template docPage;

    @Location("components/chart/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance landing() {
        return landingPage.instance();
    }

    @GET
    @Path("/{type}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance demo(@PathParam("type") String type) {
        String docTitle = DOC_TITLES.get(type);
        if (docTitle != null) {
            Template bodyTpl = engine.getTemplate("components/chart/" + type + "-doc");
            if (bodyTpl == null) {
                throw new NotFoundException("Missing doc body for: " + type);
            }
            String body = bodyTpl.instance().render();
            return docPage.instance()
                .data("type", type)
                .data("title", docTitle)
                .data("intro", DOC_INTROS.get(type))
                .data("body", body);
        }
        String title = TITLES.get(type);
        if (title == null) {
            throw new NotFoundException("Unknown chart type: " + type);
        }
        return demoPage.instance()
            .data("type", type)
            .data("title", title)
            .data("intro", INTROS.get(type));
    }

    @GET
    @Path("/{type}/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("type") String type, @PathParam("example") String example) {
        validate(type, example);
        String resourcePath = "/templates/components/chart/" + type + "/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + type + "/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/{type}/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("type") String type, @PathParam("example") String example) {
        validate(type, example);
        Template inner = engine.getTemplate("components/chart/" + type + "/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + type + "/" + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("type", type)
            .data("example", example)
            .data("content", rendered);
    }

    private void validate(String type, String example) {
        if (!TITLES.containsKey(type)) {
            throw new NotFoundException("Unknown chart type: " + type);
        }
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown example: " + example);
        }
    }
}
