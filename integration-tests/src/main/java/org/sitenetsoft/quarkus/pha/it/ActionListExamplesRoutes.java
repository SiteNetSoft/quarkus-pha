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
 * Per-example endpoints for the action-list demo page:
 *   GET /components/action-list/source/{example} → raw Qute source as text/plain
 *   GET /components/action-list/{example}        → standalone page wrapping the
 *                                                   example in layouts/base
 *
 * Lives under /components (not /components/action-list) so it doesn't collide
 * with ComponentRoutes.actionList() at /components/action-list.
 */
@Path("/components")
public class ActionListExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "single-group",
        "with-kebab",
        "icons-list",
        "icons-group",
        "multiple-groups",
        "cancel-form",
        "cancel-wizard",
        "vertical"
    );

    @Inject
    Engine engine;

    @Location("components/action-list/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/action-list/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown action-list example: " + example);
        }
        String resourcePath = "/templates/components/action-list/" + example + ".html";
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
    @Path("/action-list/source-java/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String sourceJava(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown action-list example: " + example);
        }
        String resourcePath = "/code-samples/action-list/" + example + ".java";
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
    @Path("/action-list/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown action-list example: " + example);
        }
        Template inner = engine.getTemplate("components/action-list/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
