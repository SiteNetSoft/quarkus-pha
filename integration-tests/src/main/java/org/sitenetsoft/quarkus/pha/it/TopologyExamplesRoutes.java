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
 * Routing for the 12 topology demo pages.
 *
 *   GET /components/topology                           → landing index
 *   GET /components/topology/{type}                    → shared demo page with Basic example
 *   GET /components/topology/{type}/{example}          → standalone wrapper around the example fragment
 *   GET /components/topology/{type}/source/{example}   → raw Qute source as text/plain
 */
@Path("/components/topology")
public class TopologyExamplesRoutes {

    private static final Map<String, String> TITLES = Map.ofEntries(
        Map.entry("custom-nodes",   "Custom nodes"),
        Map.entry("custom-edges",   "Custom edges"),
        Map.entry("anchors",        "Anchors"),
        Map.entry("selection",      "Selection"),
        Map.entry("pan-and-zoom",   "Pan and zoom"),
        Map.entry("context-menu",   "Context menu"),
        Map.entry("drag-and-drop",  "Drag and drop"),
        Map.entry("control-bar",    "Control bar"),
        Map.entry("toolbar",        "Toolbar"),
        Map.entry("sidebar",        "Sidebar"),
        Map.entry("layouts",        "Layouts"),
        Map.entry("pipelines",      "Pipelines")
    );

    private static final Map<String, String> INTROS = Map.ofEntries(
        Map.entry("custom-nodes",   "Drive node colour, shape and size from <code class=\"ws-code\">data</code> attributes via selector-scoped Cytoscape style rules."),
        Map.entry("custom-edges",   "Style edges by <code class=\"ws-code\">data.kind</code> — solid sync, dashed async, thick critical — without any per-edge inline styling."),
        Map.entry("anchors",        "Force edges to enter and leave nodes at fixed compass points using <code class=\"ws-code\">source-endpoint</code> / <code class=\"ws-code\">target-endpoint</code> plus the taxi curve style."),
        Map.entry("selection",      "Box-select + click select with a live Alpine readout of selected node ids. Listen for the <code class=\"ws-code\">topology-tap</code> event on the wrapper."),
        Map.entry("pan-and-zoom",   "Default mouse + trackpad pan/zoom plus floating zoom-in / zoom-out / fit buttons that call into the public <code class=\"ws-code\">phaTopology</code> API."),
        Map.entry("context-menu",   "Right-click reveals a small Alpine-driven menu positioned at the rendered tap location. No Cytoscape extension required."),
        Map.entry("drag-and-drop",  "Default Cytoscape behaviour — every node is grabbable; dragging persists positions for the life of the page."),
        Map.entry("control-bar",    "A small overlay button group anchored bottom-left of the canvas — zoom in / zoom out / fit / re-layout."),
        Map.entry("toolbar",        "A persistent toolbar above the canvas: filter chips that hide unhealthy nodes + a layout switcher dropdown."),
        Map.entry("sidebar",        "Tapping a node opens a slide-in sidebar with the node's metadata. Tapping the canvas closes it."),
        Map.entry("layouts",        "Switch between five Cytoscape layouts (cose, breadthfirst, concentric, grid, circle) with one click; the graph re-flows."),
        Map.entry("pipelines",      "CI pipeline rendered left-to-right via the <code class=\"ws-code\">breadthfirst</code> layout with a <code class=\"ws-code\">transform</code> that flips the axes. Stage colours show pipeline status.")
    );

    private static final Set<String> EXAMPLES = Set.of("basic");

    @Inject
    Engine engine;

    @Location("components/topology-demo")
    @Inject
    Template landingPage;

    @Location("components/topology/_demo-page")
    @Inject
    Template demoPage;

    @Location("components/topology/_standalone")
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
        String title = TITLES.get(type);
        if (title == null) {
            throw new NotFoundException("Unknown topology type: " + type);
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
        String resourcePath = "/templates/components/topology/" + type + "/" + example + ".html";
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
        Template inner = engine.getTemplate("components/topology/" + type + "/" + example);
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
            throw new NotFoundException("Unknown topology type: " + type);
        }
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown example: " + example);
        }
    }
}
