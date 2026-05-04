package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.startsWithIgnoringCase;

/**
 * Smoke tests for full-page routes: the homepage, every implemented
 * component demo, and every demo page. Each route must return 200,
 * be served as text/html, and contain the doctype + a closing
 * &lt;/html&gt; tag (a minimal sanity check that Qute composed the page).
 *
 * Catches Qute compilation errors and missing template bindings before
 * a single browser starts.
 */
@QuarkusTest
class RoutesSmokeTest {

    @Test
    void homepage_renders() {
        given()
            .when().get("/")
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(startsWithIgnoringCase("<!doctype html>"))
                .body(containsString("</html>"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "/components/about-modal",
        "/components/accordion",
        "/components/action-list",
        "/components/alert",
        "/components/application-launcher",
        "/components/avatar",
        "/components/back-to-top",
        "/components/backdrop",
        "/components/background-image",
        "/components/badge",
        "/components/banner",
        "/components/brand",
        "/components/breadcrumb",
        "/components/button",
        "/components/calendar-month",
        "/components/card",
        "/components/chart",
        "/components/checkbox",
        "/components/chip",
        "/components/click-to-load",
        "/components/clipboard-copy",
        "/components/code-block",
        "/components/code-editor",
        "/components/content",
        "/components/context-selector",
        "/components/custom-menus",
        "/components/data-list",
        "/components/date-and-time-picker",
        "/components/date-picker",
        "/components/description-list",
        "/components/divider",
        "/components/document-editor",
        "/components/drag-and-drop",
        "/components/drawer",
        "/components/dropdown",
        "/components/dual-list-selector",
        "/components/empty-state",
        "/components/expandable-section",
        "/components/form",
        "/components/form-control",
        "/components/form-select",
        "/components/helper-text",
        "/components/hint",
        "/components/icon",
        "/components/infinite-scroll",
        "/components/inline-edit",
        "/components/input-group",
        "/components/jump-links",
        "/components/label",
        "/components/lazy-modal",
        "/components/list",
        "/components/live-search",
        "/components/login-page",
        "/components/map",
        "/components/masthead",
        "/components/menu",
        "/components/menu-toggle",
        "/components/modal",
        "/components/multiple-file-upload",
        "/components/navigation",
        "/components/notification-badge",
        "/components/notification-drawer",
        "/components/number-input",
        "/components/options-menu",
        "/components/overflow-menu",
        "/components/page",
        "/components/pagination",
        "/components/panel",
        "/components/password-generator",
        "/components/password-strength",
        "/components/popover",
        "/components/progress",
        "/components/progress-stepper",
        "/components/radio",
        "/components/rectangle-selection",
        "/components/search-input",
        "/components/select",
        "/components/sidebar",
        "/components/simple-file-upload",
        "/components/simple-list",
        "/components/skeleton",
        "/components/skip-to-content",
        "/components/slider",
        "/components/spinner",
        "/components/switch",
        "/components/table",
        "/components/tabs",
        "/components/text-area",
        "/components/text-input",
        "/components/text-input-group",
        "/components/tile",
        "/components/time-picker",
        "/components/timestamp",
        "/components/title",
        "/components/toggle-group",
        "/components/toolbar",
        "/components/tooltip",
        "/components/tree-view",
        "/components/truncate",
        "/components/wizard"
    })
    void component_demo_renders(String path) {
        given()
            .when().get(path)
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(startsWithIgnoringCase("<!doctype html>"))
                .body(containsString("</html>"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "/demos/dashboard",
        "/demos/data-management",
        "/demos/settings",
        "/demos/landing"
    })
    void demo_page_renders(String path) {
        given()
            .when().get(path)
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(startsWithIgnoringCase("<!doctype html>"))
                .body(containsString("</html>"));
    }
}
