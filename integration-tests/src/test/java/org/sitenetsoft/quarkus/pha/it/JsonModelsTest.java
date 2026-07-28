package org.sitenetsoft.quarkus.pha.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.qute.Engine;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.sitenetsoft.quarkus.pha.model.Badge;
import org.sitenetsoft.quarkus.pha.model.Menu;
import org.sitenetsoft.quarkus.pha.model.MenuItem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The JSON view-model contract: pha models deserialize from plain JSON (builder-backed models
 * via Jackson builder support, immutable fluent models via their creator constructor) and the
 * resulting instances render through the same Qute templates as Java-built ones. Pilot models:
 * Badge (simple builder), MenuItem (immutable creator), Menu (nested builder with groups and
 * mode invariants enforced in build()).
 */
@QuarkusTest
class JsonModelsTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Inject
    Engine engine;

    @Test
    void badge_deserializes_and_renders() throws Exception {
        Badge badge = mapper.readValue(
                "{\"value\": \"7\", \"variant\": \"read\", \"screenReaderText\": \"unread notifications\"}",
                Badge.class);
        assertEquals("7", badge.value());
        assertEquals("read", badge.variant());

        String html = engine.getTemplate("components/data-display/badge").instance()
                .data("badge", badge).render();
        assertTrue(html.contains("pf-m-read"), html);
        assertTrue(html.contains(">7 <span"), html);
        assertTrue(html.contains("unread notifications"), html);
    }

    @Test
    void badge_missing_required_value_fails() {
        assertThrows(Exception.class, () -> mapper.readValue("{\"variant\": \"read\"}", Badge.class));
    }

    @Test
    void menu_item_deserializes_via_creator() throws Exception {
        MenuItem item = mapper.readValue(
                "{\"text\": \"Delete\", \"danger\": true, \"description\": \"Permanently remove\"}",
                MenuItem.class);
        assertEquals("Delete", item.text());
        assertTrue(item.isDanger());
        assertEquals("Permanently remove", item.description());
    }

    @Test
    void menu_with_items_deserializes_and_renders() throws Exception {
        Menu menu = mapper.readValue(
                """
                {"id": "mn-json", "plain": true,
                 "items": [
                   {"text": "Edit"},
                   {"divider": true},
                   {"text": "Delete", "danger": true}
                 ]}
                """, Menu.class);
        assertEquals("mn-json", menu.id());

        String html = engine.getTemplate("components/navigation/menu").instance()
                .data("menu", menu).render();
        assertTrue(html.contains("pf-m-plain"), html);
        assertTrue(html.contains("Edit"), html);
        assertTrue(html.contains("pf-m-danger"), html);
        assertTrue(html.contains("pf-v6-c-divider"), html);
    }

    @Test
    void menu_with_groups_and_select_mode_deserializes() throws Exception {
        Menu menu = mapper.readValue(
                """
                {"id": "mn-json-groups", "selectMode": "single",
                 "groups": [
                   {"title": "Compute", "items": [{"text": "Pods"}, {"text": "Deployments", "selected": true}]},
                   {"title": "Network", "items": [{"text": "Services"}]}
                 ]}
                """, Menu.class);
        String html = engine.getTemplate("components/navigation/menu").instance()
                .data("menu", menu).render();
        assertTrue(html.contains("Compute"), html);
        assertTrue(html.contains("menuitemradio"), html);
    }

    @Test
    void table_with_nested_rows_deserializes_and_renders() throws Exception {
        org.sitenetsoft.quarkus.pha.model.Table table = mapper.readValue(
                """
                {"id": "tbl-json", "ariaLabel": "Users", "compact": true,
                 "columns": [{"label": "Name"}, {"label": "Status"}],
                 "rows": [
                   {"cells": [{"kind": "TEXT", "text": "John"}, {"kind": "TEXT", "text": "Active"}]},
                   {"cells": [{"kind": "TEXT", "text": "Jane"}, {"kind": "TEXT", "text": "Paused"}]}
                 ]}
                """, org.sitenetsoft.quarkus.pha.model.Table.class);
        String html = engine.getTemplate("components/data-display/table").instance()
                .data("table", table).render();
        assertTrue(html.contains("pf-m-compact"), html);
        assertTrue(html.contains("John"), html);
        assertTrue(html.contains("Paused"), html);
    }

    @Test
    void menu_build_invariants_still_apply_to_json() {
        // build() rejects an empty menu — the JSON path goes through the same builder.
        assertThrows(Exception.class, () -> mapper.readValue("{\"id\": \"mn-empty\"}", Menu.class));
    }
}
