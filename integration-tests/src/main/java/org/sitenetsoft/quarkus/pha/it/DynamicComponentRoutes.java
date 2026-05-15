package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateException;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

/**
 * Prototype: one route handles every component by resolving the template
 * dynamically and pulling demo data from a registry. Compare to
 * ComponentRoutes which needs a field + method per component.
 */
@Path("/components-v2")
public class DynamicComponentRoutes {

    @Inject
    Engine engine;

    private static final Map<String, Map<String, Object>> DEMO_DATA = Map.ofEntries(
        Map.entry("breadcrumb", Map.of(
            "basicItems", List.of(
                Map.of("text", "Section home", "href", "#"),
                Map.of("text", "Section title", "href", "#"),
                Map.of("text", "Section title", "href", "#"),
                Map.of("text", "Section landing", "href", "#", "current", true)
            ),
            "noHomeItems", List.of(
                Map.of("text", "Section home"),
                Map.of("text", "Section title", "href", "#"),
                Map.of("text", "Section title", "href", "#"),
                Map.of("text", "Section title", "href", "#"),
                Map.of("text", "Section landing", "href", "#", "current", true)
            ),
            "headingItems", List.of(
                Map.of("text", "Section home", "href", "#"),
                Map.of("text", "Section title", "href", "#"),
                Map.of("text", "Section title", "href", "#"),
                Map.of("text", "Section landing", "href", "#", "heading", true)
            )
        )),
        Map.entry("alert", Map.of(
            "sampleActionLinks", List.of(
                Map.of("text", "View details", "href", "#"),
                Map.of("text", "Ignore", "href", "#")
            )
        ))
    );

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance render(@PathParam("name") String name) {
        Template template;
        try {
            template = engine.getTemplate("components/" + name);
        } catch (TemplateException e) {
            throw new NotFoundException("Unknown component: " + name);
        }
        if (template == null) {
            throw new NotFoundException("Unknown component: " + name);
        }
        TemplateInstance instance = template.instance();
        Map<String, Object> data = DEMO_DATA.get(name);
        if (data != null) {
            data.forEach(instance::data);
        }
        return instance;
    }
}
