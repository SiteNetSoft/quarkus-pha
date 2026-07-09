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
 * Per-example endpoints for the label demo page:
 *   GET /components/label/source/{example} → raw Qute source as text/plain
 *   GET /components/label/{example}        → standalone page wrapping the
 *                                             example in layouts/base
 *
 * Lives under /components (not /components/label) so it doesn't collide
 * with ComponentRoutes.label() at /components/label.
 */
@Path("/components")
public class LabelExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "filled-labels",
        "outlined-labels",
        "compact-labels",
        "label-with-custom-render",
        "editable-labels",
        "basic-label-group",
        "label-group-with-overflow",
        "label-group-with-categories",
        "label-group-with-removable-categories",
        "vertical-label-group",
        "editable-label-group",
        "editable-label-group-with-add-button",
        "overflow-label",
        "add-label",
        "label-group-with-overflow-expanded",
        "vertical-label-group-with-overflow",
        "vertical-label-group-with-overflow-expanded",
        "vertical-label-group-with-category",
        "vertical-label-group-with-removable-category",
        "static-labels-dynamic-group",
        "mixed-labels-dynamic-group",
        "label-group-with-compact-labels",
        "label-group-with-compact-labels-and-overflow",
        "vertical-label-group-with-compact-labels",
        "labels-with-truncation"
    );

    @Inject
    Engine engine;

    @Location("components/label/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/label/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown label example: " + example);
        }
        String resourcePath = "/templates/components/label/" + example + ".html";
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
    @Path("/label/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown label example: " + example);
        }
        Template inner = engine.getTemplate("components/label/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
