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
 * Per-example endpoints for the button demo page:
 *   GET /components/button/source/{example} → raw Qute source as text/plain
 *   GET /components/button/{example}        → standalone page wrapping the
 *                                              example in layouts/base
 *
 * Lives under /components (not /components/button) so it doesn't collide
 * with ComponentRoutes.button() at /components/button.
 */
@Path("/components")
public class ButtonExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "variant-examples",
        "disabled-buttons",
        "small-buttons",
        "call-to-action",
        "block-level",
        "progress-indicators",
        "links-as-buttons",
        "inline-link-as-span",
        "custom-component",
        "aria-disabled-examples",
        "button-with-count",
        "plain-with-no-padding",
        "stateful",
        "favorite",
        "settings",
        "hamburger"
    );

    @Inject
    Engine engine;

    @Location("components/button/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/button/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown button example: " + example);
        }
        String resourcePath = "/templates/components/button/" + example + ".html";
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
    @Path("/button/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown button example: " + example);
        }
        Template inner = engine.getTemplate("components/button/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
