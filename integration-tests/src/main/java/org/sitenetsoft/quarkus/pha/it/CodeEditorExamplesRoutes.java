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
 * Per-example endpoints for the code-editor demo page:
 *   GET /components/code-editor/source/{example} → raw Qute source as text/plain
 *   GET /components/code-editor/{example}        → standalone page wrapping the example
 */
@Path("/components")
public class CodeEditorExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "basic",
        "read-only",
        "without-actions",
        "minimal",
        "upload",
        "header-content",
        "container",
        "full-height",
        "custom-control"
    );

    @Inject
    Engine engine;

    @Location("components/code-editor/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/code-editor/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown code-editor example: " + example);
        }
        String resourcePath = "/templates/components/code-editor/" + example + ".html";
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
    @Path("/code-editor/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown code-editor example: " + example);
        }
        Template inner = engine.getTemplate("components/code-editor/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
