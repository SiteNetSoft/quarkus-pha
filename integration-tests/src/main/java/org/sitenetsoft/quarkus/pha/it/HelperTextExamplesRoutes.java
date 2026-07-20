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
 * Per-example endpoints for the helper-text demo page:
 *   GET /components/helper-text/source/{example} → raw Qute source as text/plain
 *   GET /components/helper-text/{example}        → standalone page wrapping the
 *                                                   example in layouts/base
 *
 * Lives under /components (not /components/helper-text) so it doesn't collide
 * with ComponentRoutes.helperText() at /components/helper-text.
 */
@Path("/components")
public class HelperTextExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "basic",
        "with-custom-icons",
        "multiple-items"
    );

    @Inject
    Engine engine;

    @Location("components/helper-text/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/helper-text/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown helper-text example: " + example);
        }
        String resourcePath = "/templates/components/helper-text/" + example + ".html";
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
    @Path("/helper-text/source-java/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sourceJava(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown helper-text example: " + example);
        }
        String resourcePath = "/code-samples/helper-text/" + example + ".java";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing Java sample for: " + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/helper-text/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown helper-text example: " + example);
        }
        Template inner = engine.getTemplate("components/helper-text/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
