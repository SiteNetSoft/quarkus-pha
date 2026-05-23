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
import java.util.List;
import java.util.Map;
import java.util.Set;

@Path("/components")
public class AccordionExamplesRoutes {

    private static final Set<String> EXAMPLES = Set.of(
        "definition-list",
        "single-expand",
        "fixed-multiple",
        "bordered",
        "toggle-start"
    );

    static final List<Map<String, Object>> SAMPLE_ITEMS = List.of(
        Map.of("title", "Item one",
               "content", "This is the expandable content for item one. It provides details and additional information."),
        Map.of("title", "Item two",
               "content", "This is the expandable content for item two. It provides details and additional information."),
        Map.of("title", "Item three",
               "content", "This is the expandable content for item three. It provides details and additional information."),
        Map.of("title", "Item four",
               "content", "This is the expandable content for item four. It provides details and additional information.")
    );

    @Inject
    Engine engine;

    @Location("components/accordion/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Path("/accordion/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown accordion example: " + example);
        }
        String resourcePath = "/templates/components/accordion/" + example + ".html";
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
    @Path("/accordion/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("example") String example) {
        if (!EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown accordion example: " + example);
        }
        Template inner = engine.getTemplate("components/accordion/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance()
            .data("sampleItems", SAMPLE_ITEMS)
            .render();
        return standalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
