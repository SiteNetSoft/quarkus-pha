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
import java.util.Map;
import java.util.Set;

/**
 * Routes for the PatternFly v6 layout primitives — Bullseye, Flex, Gallery, Grid, Level, Split, Stack.
 *
 * One demo page per layout at /layouts/{name}, one standalone example route at
 * /layouts/{name}/{slug}, and one source endpoint at /layouts/{name}/source/{slug}.
 *
 * The example allow-list lives in EXAMPLES; the standalone wrapper is shared
 * across all layouts at templates/layouts/_standalone.html.
 */
@Path("/layouts")
public class LayoutsRoutes {

    /** Allowlist of valid example slugs per layout. */
    private static final Map<String, Set<String>> EXAMPLES = Map.of(
        "bullseye", Set.of("basic", "with-content"),
        "flex", Set.of("basic", "direction-column", "with-gap", "justify-content", "nested", "nested-with-items", "spacing-parent", "spacing-children", "spacing-parent-children", "row-gap", "column-gap", "default-layout", "inline-flex", "grow", "grow-1", "grow-1-4", "nested-columns", "responsive-direction", "align-right", "align-right-single", "align-right-groups", "align-self-flex-end", "align-self-center", "align-self-baseline", "align-self-stretch", "justify-content-flex-end", "justify-content-space-between", "justify-content-flex-start", "ordering", "ordering-first-last", "ordering-first-last-responsive", "ordering-responsive"),
        "gallery", Set.of("basic", "with-gutter", "custom-min-width", "max-width", "min-width-responsive", "max-width-responsive", "min-max-width-responsive", "as-list"),
        "grid", Set.of("basic", "with-gutter", "spans", "responsive-spans", "smart", "smart-overrides", "nested", "offsets", "row-spans", "as-list", "ordering", "ordering-responsive", "ordering-grouped", "ordering-grouped-responsive"),
        "level", Set.of("basic", "with-gutter", "three-items"),
        "split", Set.of("basic", "with-fill", "with-gutter", "wrappable"),
        "stack",    Set.of("basic", "with-fill", "with-gutter")
    );

    @Inject
    Engine engine;

    @Location("layouts/_standalone")
    @Inject
    Template standalonePage;

    /** Demo page for a layout — looks up templates/layouts/{name}.html via the engine. */
    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance demo(@PathParam("name") String name) {
        if (!EXAMPLES.containsKey(name)) {
            throw new NotFoundException("Unknown layout: " + name);
        }
        Template demo = engine.getTemplate("layouts/" + name);
        if (demo == null) {
            throw new NotFoundException("Demo template not found: layouts/" + name);
        }
        return demo.instance()
            .data("name", name)
            .data("hasDemoCss", hasDemoCss(name));
    }

    /** Raw Qute source of an example fragment. */
    @GET
    @Path("/{name}/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        String resourcePath = "/templates/layouts/" + name + "/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + name + "/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    /** Standalone page wrapping the example fragment in layouts/base. */
    @GET
    @Path("/{name}/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("name") String name, @PathParam("example") String example) {
        validate(name, example);
        Template inner = engine.getTemplate("layouts/" + name + "/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found: layouts/" + name + "/" + example);
        }
        String rendered = inner.instance().render();
        return standalonePage.instance()
            .data("name", name)
            .data("example", example)
            .data("content", rendered)
            .data("hasDemoCss", hasDemoCss(name));
    }

    private boolean hasDemoCss(String name) {
        return getClass().getResource("/META-INF/resources/web/css/demos/layouts/" + name + ".css") != null;
    }

    private void validate(String name, String example) {
        Set<String> allowed = EXAMPLES.get(name);
        if (allowed == null || !allowed.contains(example)) {
            throw new NotFoundException("Unknown layout/example: " + name + "/" + example);
        }
    }
}
