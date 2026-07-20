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
 * Per-example endpoints for the spinner demo page:
 *   GET /components/spinner/source/{example} → raw Qute source as text/plain
 *   GET /components/spinner/{example}        → standalone page wrapping the
 *                                               example in layouts/base
 *
 * Lives under /components (not /components/spinner) so it doesn't collide
 * with ComponentRoutes.spinner() at /components/spinner.
 *
 * Example names are allowlisted so this route doesn't double as an arbitrary
 * classpath resource reader.
 */
@Path("/components")
public class SpinnerExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "basic",
        "size-variations",
        "custom-size",
        "inline"
    );

    @Inject
    Engine engine;

    @Location("components/spinner/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/spinner/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown spinner example: " + example);
        }
        String resourcePath = "/templates/components/spinner/" + example + ".html";
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
    @Path("/spinner/source-java/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sourceJava(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown spinner example: " + example);
        }
        String resourcePath = "/code-samples/spinner/" + example + ".java";
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
    @Path("/spinner/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown spinner example: " + example);
        }
        Template inner = engine.getTemplate("components/spinner/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
