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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * Per-example endpoints for the seven form components — Form, Form control,
 * Checkbox, Form select, Radio, Text area, Text input.
 *
 * One Java class handles all of them via a name+example allowlist. Each form
 * subdirectory carries its own _standalone.html wrapper.
 *
 *   GET /components/{name}/source/{example} → raw Qute source as text/plain
 *   GET /components/{name}/{example}        → standalone wrapped in layouts/base
 *
 * The demo pages at /components/{name} are still served by ComponentRoutes; this
 * class only handles the per-example routes.
 */
@Path("/components")
public class FormsExamplesRoutes {

    private static final Map<String, Set<String>> EXAMPLES = Map.of(
        "form",         Set.of("basic", "horizontal", "limit-width", "invalid", "invalid-form-alert", "validated",
                               "horizontal-stacked", "horizontal-helper-on-top", "group-label-info", "sections",
                               "grid", "field-groups", "with-helper"),
        "form-control", Set.of("basic", "validated", "disabled-readonly"),
        "checkbox",     Set.of("basic", "controlled", "checked", "disabled", "reversed", "label-wraps",
                               "with-description", "with-body", "description-and-body", "required", "standalone"),
        "form-select",  Set.of("basic", "grouped", "validated", "disabled"),
        "radio",        Set.of("basic", "controlled", "reversed", "label-wraps", "disabled", "with-description",
                               "with-body", "description-and-body", "standalone"),
        "text-area",    Set.of("basic", "invalid", "validated", "resize-vertical", "horizontal-resizable",
                               "not-resizable", "disabled", "read-only", "disabled-readonly", "auto-resizing"),
        "text-input",   Set.of("basic", "disabled", "readonly", "invalid", "with-icon", "icon-invalid",
                               "start-truncated", "types")
    );

    @Inject
    Engine engine;

    @GET
    @Path("/{name:(form|form-control|checkbox|form-select|radio|text-area|text-input)}/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        String resourcePath = "/templates/components/" + name + "/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + name + "/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/{name:(form|form-control|checkbox|form-select|radio|text-area|text-input)}/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        Template inner = engine.getTemplate("components/" + name + "/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found: " + name + "/" + example);
        }
        Template standalone = engine.getTemplate("components/" + name + "/_standalone");
        if (standalone == null) {
            throw new NotFoundException("Standalone wrapper missing for: " + name);
        }
        String rendered = inner.instance().render();
        return standalone.instance()
            .data("name", name)
            .data("example", example)
            .data("content", rendered);
    }

    private void validate(String name, String example) {
        Set<String> allowed = EXAMPLES.get(name);
        if (allowed == null || !allowed.contains(example)) {
            throw new NotFoundException("Unknown forms example: " + name + "/" + example);
        }
    }
}
