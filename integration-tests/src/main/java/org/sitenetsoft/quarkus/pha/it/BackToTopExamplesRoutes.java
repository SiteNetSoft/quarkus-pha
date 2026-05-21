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
 * Per-example endpoints for the back-to-top demo page:
 *   GET /components/back-to-top/source/{example} → raw Qute source as text/plain
 *   GET /components/back-to-top/{example}        → standalone page wrapping the
 *                                                   example in layouts/base
 *
 * Lives under /components (not /components/back-to-top) so it doesn't collide
 * with ComponentRoutes.backToTop() at /components/back-to-top.
 */
@Path("/components")
public class BackToTopExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of("basic");

    @Inject
    Engine engine;

    @Location("components/back-to-top/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/back-to-top/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown back-to-top example: " + example);
        }
        String resourcePath = "/templates/components/back-to-top/" + example + ".html";
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
    @Path("/back-to-top/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown back-to-top example: " + example);
        }
        Template inner = engine.getTemplate("components/back-to-top/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
