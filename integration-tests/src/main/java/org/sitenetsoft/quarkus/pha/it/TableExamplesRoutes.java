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
import java.util.Set;

/**
 * Per-example endpoints for the table demo page:
 *   GET /components/table/source/{example} → raw Qute source as text/plain
 *   GET /components/table/{example}        → standalone page wrapping the
 *                                            example fragment in layouts/base
 *
 * The "sortable" example posts to {@code /api/htmx/table-sort} in HtmxRoutes;
 * the source/standalone routes here only serve the static fragments.
 */
@Path("/components")
public class TableExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "basic",
        "compact",
        "striped",
        "borderless",
        "sortable",
        "expandable",
        "selectable-checkbox",
        "selectable-radio",
        "actions",
        "empty-state",
        "width",
        "favoritable",
        "clickable-rows",
        "nested-column-headers",
        "editable-rows",
        "compound-expandable",
        "sticky",
        "tree-table",
        "draggable-rows",
        "footer"
    );

    @Inject
    Engine engine;

    @Location("components/table/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/table/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown table example: " + example);
        }
        String resourcePath = "/templates/components/table/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/table/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown table example: " + example);
        }
        Template inner = engine.getTemplate("components/table/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
