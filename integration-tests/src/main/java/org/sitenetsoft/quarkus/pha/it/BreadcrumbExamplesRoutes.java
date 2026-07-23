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
 * Per-example endpoints for the breadcrumb demo page:
 *   GET /components/breadcrumb/source/{example} → raw Qute source as text/plain
 *   GET /components/breadcrumb/{example}        → standalone page wrapping the
 *                                                  example in layouts/base
 *
 * Lives under /components (not /components/breadcrumb) so it doesn't collide
 * with ComponentRoutes.breadcrumb() at /components/breadcrumb.
 */
@Path("/components")
public class BreadcrumbExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "basic",
        "without-home-link",
        "with-heading",
        "with-dropdown",
        "auto-generated",
            "with-buttons");

    @Inject
    Engine engine;

    @Location("components/breadcrumb/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/breadcrumb/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown breadcrumb example: " + example);
        }
        String resourcePath = "/templates/components/breadcrumb/" + example + ".html";
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
    @Path("/breadcrumb/source-java/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sourceJava(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown breadcrumb example: " + example);
        }
        String resourcePath = "/code-samples/breadcrumb/" + example + ".java";
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
    @Path("/breadcrumb/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown breadcrumb example: " + example);
        }
        Template inner = engine.getTemplate("components/breadcrumb/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        var innerInstance = inner.instance();
        if ("auto-generated".equals(example)) {
            innerInstance.data("autoTrail", BreadcrumbTrail.fromPath("/components/breadcrumb"));
        }
        String rendered = innerInstance.render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
