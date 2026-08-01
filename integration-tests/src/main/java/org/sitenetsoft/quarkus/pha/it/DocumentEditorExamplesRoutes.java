package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * Per-example endpoints for the document-editor component. Unlike the MiscExamplesRoutes
 * components, these fragments reference route-supplied data (Collabora URL, WOPI endpoints,
 * access token), so the standalone route must inject the same data the demo page passes —
 * hence a dedicated class instead of a MiscExamplesRoutes entry.
 */
@Path("/components")
public class DocumentEditorExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "basic", "text-document", "spreadsheet", "presentation",
        "with-toolbar", "readonly", "with-status");

    @ConfigProperty(name = "collabora.url")
    String collaboraUrl;

    @ConfigProperty(name = "collabora.wopi-host")
    String collaboraWopiHost;

    @ConfigProperty(name = "collabora.access-token")
    String collaboraAccessToken;

    @Inject
    Engine engine;

    @GET
    @Path("/document-editor/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        validate(example);
        String resourcePath = "/templates/components/document-editor/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: document-editor/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/document-editor/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        validate(example);
        Template inner = engine.getTemplate("components/document-editor/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found: document-editor/" + example);
        }
        Template standalone = engine.getTemplate("components/document-editor/_standalone");
        String rendered = inner.instance()
            .data("collaboraUrl", collaboraUrl)
            .data("accessToken", collaboraAccessToken)
            .data("wopiWelcome", collaboraWopiHost + "/wopi/files/welcome")
            .data("wopiBudget",  collaboraWopiHost + "/wopi/files/budget")
            .data("wopiSlides",  collaboraWopiHost + "/wopi/files/slides")
            .data("wopiPolicy",  collaboraWopiHost + "/wopi/files/policy")
            .render();
        return standalone.instance()
            .data("name", "document-editor")
            .data("example", example)
            .data("content", rendered);
    }

    private void validate(String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown document-editor example: " + example);
        }
    }
}
