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

/**
 * Routing for the /error-communication top-level bucket — PatternFly's
 * error-communication component group. Each is a reusable runtime template
 * (components/error-communication/{name}) that composes empty-state or modal.
 *
 *   GET /error-communication                   → landing card grid
 *   GET /error-communication/{name}            → shared demo page
 *   GET /error-communication/{name}/standalone → the live component in layouts/base
 *   GET /error-communication/{name}/source     → raw runtime-template source as text/plain
 */
@Path("/error-communication")
public class ErrorCommunicationRoutes {

    private static final Map<String, String> TITLES = Map.ofEntries(
        Map.entry("error-state",          "Error state"),
        Map.entry("missing-page",         "Missing page"),
        Map.entry("maintenance",          "Maintenance"),
        Map.entry("unauthorized-access",  "Unauthorized access"),
        Map.entry("unavailable-content",  "Unavailable content"),
        Map.entry("warning-modal",        "Warning modal"),
        Map.entry("error-boundary",       "Error boundary")
    );

    private static final Map<String, String> INTROS = Map.ofEntries(
        Map.entry("error-state",          "A danger empty-state for unexpected failures — \"Something went wrong\" with a path back to safety. The building block the rest of this group composes."),
        Map.entry("missing-page",         "The 404. Tells the user the page is gone and offers a way back home."),
        Map.entry("maintenance",          "A scheduled-maintenance screen for planned downtime, with a link to a status page."),
        Map.entry("unauthorized-access",  "The 403. A lock icon, a \"you do not have access\" message, and a way to the landing or previous page."),
        Map.entry("unavailable-content",  "A temporary-unavailability state for content that failed to load — suggests a refresh and links to a status page."),
        Map.entry("warning-modal",        "A confirm dialog for risky / destructive actions. Composes the modal (warning variant) with Confirm / Cancel."),
        Map.entry("error-boundary",       "Repurposes the error-state as an application error fallback, with an optional page header and expandable diagnostics. React catches client render errors; a server-rendered app renders this when a handler throws or an HTMX request fails.")
    );

    private static final List<String> ORDER = List.of(
        "error-state", "missing-page", "maintenance", "unauthorized-access",
        "unavailable-content", "warning-modal", "error-boundary"
    );

    @Inject
    Engine engine;

    @Location("error-communication/index")
    @Inject
    Template landingPage;

    @Location("error-communication/_demo-page")
    @Inject
    Template demoPage;

    @Location("error-communication/_standalone")
    @Inject
    Template standalonePage;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance landing() {
        List<Map<String, String>> components = ORDER.stream()
            .map(slug -> Map.of("slug", slug, "title", TITLES.get(slug), "intro", INTROS.get(slug)))
            .toList();
        return landingPage.instance().data("components", components);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance demo(@PathParam("name") String name) {
        String title = TITLES.get(name);
        if (title == null) {
            throw new NotFoundException("Unknown error-communication component: " + name);
        }
        return demoPage.instance()
            .data("type", name)
            .data("title", title)
            .data("intro", INTROS.get(name));
    }

    @GET
    @Path("/{name}/standalone")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance standalone(@PathParam("name") String name) {
        if (!TITLES.containsKey(name)) {
            throw new NotFoundException("Unknown error-communication component: " + name);
        }
        Template inner = engine.getTemplate("components/error-communication/" + name);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + name);
        }
        // Only warning-modal needs an id (modal aria + open event); passing it to the
        // empty-state-based ones would leak the id onto their composed action buttons.
        var instance = inner.instance();
        if (name.equals("warning-modal")) {
            instance.data("id", "warning-modal-modal");
        }
        String rendered = instance.render();
        return standalonePage.instance()
            .data("name", name)
            .data("title", TITLES.get(name))
            .data("content", rendered);
    }

    @GET
    @Path("/{name}/source")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(@PathParam("name") String name) {
        if (!TITLES.containsKey(name)) {
            throw new NotFoundException("Unknown error-communication component: " + name);
        }
        String resourcePath = "/templates/components/error-communication/" + name + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + name);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }
}
