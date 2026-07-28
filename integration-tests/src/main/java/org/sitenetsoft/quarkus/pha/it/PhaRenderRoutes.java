package org.sitenetsoft.quarkus.pha.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * The VDP flow, previewed as one endpoint: a {@code pha:} template URI (what a descriptor names)
 * plus a JSON payload (what the API returns) renders server-side PatternFly HTML through the
 * JSON-constructible models — no Java builder code anywhere in the request path.
 *
 * <p>Also the native-image smoke target: this path exercises Jackson's reflective builder/creator
 * access and the PhaTemplateLocator's runtime resource reads — the two things a native build is
 * most likely to break (see .github/workflows/native.yml).
 */
@Path("/api/pha")
public class PhaRenderRoutes {

    /** The payload types this preview accepts, keyed by the template data name they bind to. */
    private static final Map<String, Class<?>> MODELS = Map.of(
        "badge", org.sitenetsoft.quarkus.pha.model.Badge.class,
        "menu", org.sitenetsoft.quarkus.pha.model.Menu.class,
        "table", org.sitenetsoft.quarkus.pha.model.Table.class);

    private static final Pattern TEMPLATE_URI = Pattern.compile("pha:components/[A-Za-z0-9/_-]+");

    @Inject
    Engine engine;

    @Inject
    ObjectMapper mapper;

    @POST
    @Path("/render")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public String render(@QueryParam("template") String template, @QueryParam("model") String model,
            String payload) {
        if (template == null || !TEMPLATE_URI.matcher(template).matches()) {
            throw new BadRequestException("template must be a pha:components/... URI");
        }
        Class<?> type = MODELS.get(model);
        if (type == null) {
            throw new BadRequestException("Unknown model: " + model + " (one of " + MODELS.keySet() + ")");
        }
        Template t = engine.getTemplate(template);
        if (t == null) {
            throw new NotFoundException("Template not found: " + template);
        }
        try {
            return t.data(model, mapper.readValue(payload, type)).render();
        } catch (com.fasterxml.jackson.core.JacksonException e) {
            throw new BadRequestException("Payload does not bind to " + type.getSimpleName() + ": "
                    + e.getOriginalMessage());
        }
    }
}
