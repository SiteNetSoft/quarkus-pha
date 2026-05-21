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
 * Per-example endpoints for the list demo page:
 *   GET /components/list/source/{example} → raw Qute source as text/plain
 *   GET /components/list/{example}        → standalone page wrapping the
 *                                            example in layouts/base
 *
 * Lives under /components (not /components/list) so it doesn't collide with
 * ComponentRoutes.list() at /components/list.
 */
@Path("/components")
public class ListExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "basic",
        "inline",
        "ordered",
        "plain",
        "with-horizontal-rules",
        "with-icons",
        "with-large-icons"
    );

    @Inject
    Engine engine;

    @Location("components/list/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/list/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown list example: " + example);
        }
        String resourcePath = "/templates/components/list/" + example + ".html";
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
    @Path("/list/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown list example: " + example);
        }
        Template inner = engine.getTemplate("components/list/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
