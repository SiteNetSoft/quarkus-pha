package org.sitenetsoft.quarkus.pha.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.Engine;
import io.quarkus.qute.Template;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.sitenetsoft.quarkus.pha.model.Badge;
import org.sitenetsoft.quarkus.pha.model.Menu;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The {@code pha:} template-URI scheme — the VDP bridge point. A descriptor-style URI resolves
 * to any classpath template (pha's own components and the application's templates alike), and
 * combined with the JSON-constructible models the whole loop runs without a line of Java
 * builder code: URI + JSON payload → model → rendered PatternFly HTML.
 */
@QuarkusTest
class PhaTemplateUriTest {

    @Inject
    Engine engine;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void pha_uri_resolves_a_runtime_component_template() {
        Template t = engine.getTemplate("pha:components/data-display/badge");
        String html = t.data("badge", Badge.of("7").variant("read").build()).render();
        assertTrue(html.contains("pf-v6-c-badge"), html);
        assertTrue(html.contains("pf-m-read"), html);
    }

    @Test
    void descriptor_uri_plus_json_payload_renders_without_java_builders() throws Exception {
        // What the VDP bridge will do: template URI from the descriptor, payload from the API.
        String templateUri = "pha:components/navigation/menu";
        String payload = """
                {"id": "mn-vdp", "items": [{"text": "Edit"}, {"text": "Delete", "danger": true}]}
                """;
        String html = engine.getTemplate(templateUri)
                .data("menu", mapper.readValue(payload, Menu.class))
                .render();
        assertTrue(html.contains("mn-vdp"), html);
        assertTrue(html.contains("pf-m-danger"), html);
    }

    @Test
    void pha_uri_resolves_application_templates_too() {
        // The app's own fragments are addressable — the template-jar distribution flow.
        Template t = engine.getTemplate("pha:components/backdrop/basic");
        assertTrue(t.render().contains("pf-v6-c-backdrop"));
    }

    @Test
    void traversal_and_malformed_uris_do_not_resolve() {
        assertNull(engine.getTemplate("pha:../application"));
        assertNull(engine.getTemplate("pha:/etc/passwd"));
        assertNull(engine.getTemplate("pha:components/../../secrets"));
        assertNull(engine.getTemplate("pha:definitely/not/a/template"));
    }
}
