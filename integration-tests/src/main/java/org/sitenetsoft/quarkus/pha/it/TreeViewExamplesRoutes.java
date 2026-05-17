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
 * Per-example endpoints for the tree-view demo page:
 *   GET /components/tree-view/source/{example} → raw Qute source as text/plain
 *   GET /components/tree-view/{example}        → standalone page wrapping the
 *                                                 example in layouts/base
 *
 * Lives under /components (not /components/tree-view) so it doesn't collide
 * with ComponentRoutes.treeView() at /components/tree-view.
 *
 * Example names are allowlisted to keep the route from doubling as an
 * arbitrary classpath resource reader.
 */
@Path("/components")
public class TreeViewExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "single-selectable"
    );

    @Inject
    Engine engine;

    @Location("components/tree-view/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/tree-view/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown tree-view example: " + example);
        }
        String resourcePath = "/templates/components/tree-view/" + example + ".html";
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
    @Path("/tree-view/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown tree-view example: " + example);
        }
        Template inner = engine.getTemplate("components/tree-view/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
