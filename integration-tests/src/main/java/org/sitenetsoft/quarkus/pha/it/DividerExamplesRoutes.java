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
 * Per-example endpoints for the divider demo page:
 *   GET /components/divider/source/{example} → raw Qute source as text/plain
 *   GET /components/divider/{example}        → standalone page wrapping the
 *                                               example in layouts/base
 *
 * Lives under /components (not /components/divider) so it doesn't collide
 * with ComponentRoutes.divider() at /components/divider.
 */
@Path("/components")
public class DividerExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "using-hr",
        "using-li",
        "using-div",
        "inset-medium",
        "inset-at-various-breakpoints",
        "vertical-in-flex-layout",
        "vertical-in-flex-layout-inset-small",
        "vertical-in-flex-layout-inset-at-various-breakpoints",
        "switch-orientation-at-various-breakpoints"
    );

    @Inject
    Engine engine;

    @Location("components/divider/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/divider/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown divider example: " + example);
        }
        String resourcePath = "/templates/components/divider/" + example + ".html";
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
    @Path("/divider/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown divider example: " + example);
        }
        Template inner = engine.getTemplate("components/divider/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
