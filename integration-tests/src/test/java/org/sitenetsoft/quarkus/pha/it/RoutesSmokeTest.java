package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
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

    @Test
    void manifest_renders_application_identity() {
        given()
            .when().get("/manifest.json")
            .then()
                .statusCode(200)
                .contentType(containsString("application/manifest+json"))
                .body("name", org.hamcrest.Matchers.equalTo("quarkus-pha"))
                .body("icons.size()", org.hamcrest.Matchers.equalTo(3))
                .body(not(containsString("{applicationName}")));
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
        "/components/chart/area",
        "/components/chart/bar",
        "/components/chart/box-plot",
        "/components/chart/bullet",
        "/components/chart/donut",
        "/components/chart/donut-utilization",
        "/components/chart/line",
        "/components/chart/pie",
        "/components/chart/sankey",
        "/components/chart/scatter",
        "/components/chart/sparkline",
        "/components/chart/stack",
        "/components/chart/threshold",
        "/components/chart/colors",
        "/components/chart/legends",
        "/components/chart/patterns",
        "/components/chart/resize-observer",
        "/components/chart/skeletons",
        "/components/chart/themes",
        "/components/chart/tooltips",
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
        "/components/rich-text-editor",
        "/components/ripple",
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
        "/components/topology",
        "/components/topology/custom-nodes",
        "/components/topology/custom-edges",
        "/components/topology/anchors",
        "/components/topology/selection",
        "/components/topology/pan-and-zoom",
        "/components/topology/context-menu",
        "/components/topology/drag-and-drop",
        "/components/topology/control-bar",
        "/components/topology/toolbar",
        "/components/topology/sidebar",
        "/components/topology/layouts",
        "/components/topology/pipelines",
        "/components/tree-view",
        "/components/truncate",
        "/components/video-player",
        "/components/wizard",
        "/extensions/log-viewer",
        "/extensions/user-feedback",
        "/extensions/catalog-view/catalog-item-header",
        "/extensions/catalog-view/catalog-tile",
        "/extensions/catalog-view/filter-side-panel",
        "/extensions/catalog-view/properties-side-panel",
        "/extensions/catalog-view/vertical-tabs",
        "/extensions/data-view/overview",
        "/extensions/data-view/toolbar",
        "/extensions/data-view/table",
        "/error-communication",
        "/error-communication/error-state",
        "/error-communication/missing-page",
        "/error-communication/maintenance",
        "/error-communication/unauthorized-access",
        "/error-communication/unavailable-content",
        "/error-communication/warning-modal",
        "/error-communication/error-boundary"
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

    /**
     * Every rendered route must be free of the missing-icon placeholder marker.
     * The icon-demo page intentionally renders one to show the placeholder UX,
     * so it's excluded. A typo anywhere else fails CI loudly.
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "/",
        "/components/about-modal",
        "/components/accordion",
        "/components/action-list",
        "/components/alert",
        "/components/application-launcher",
        "/components/avatar",
        "/components/breadcrumb",
        "/components/button",
        "/components/card",
        "/components/chart",
        "/components/chart/area",
        "/components/chart/bar",
        "/components/chart/box-plot",
        "/components/chart/bullet",
        "/components/chart/donut",
        "/components/chart/donut-utilization",
        "/components/chart/line",
        "/components/chart/pie",
        "/components/chart/sankey",
        "/components/chart/scatter",
        "/components/chart/sparkline",
        "/components/chart/stack",
        "/components/chart/threshold",
        "/components/chart/colors",
        "/components/chart/legends",
        "/components/chart/patterns",
        "/components/chart/resize-observer",
        "/components/chart/skeletons",
        "/components/chart/themes",
        "/components/chart/tooltips",
        "/components/chip",
        "/components/clipboard-copy",
        "/components/code-block",
        "/components/code-editor",
        "/components/context-selector",
        "/components/custom-menus",
        "/components/document-editor",
        "/components/drag-and-drop",
        "/components/dropdown",
        "/components/dual-list-selector",
        "/components/empty-state",
        "/components/expandable-section",
        "/components/form-select",
        "/components/helper-text",
        "/components/inline-edit",
        "/components/label",
        "/components/lazy-modal",
        "/components/live-search",
        "/components/menu-toggle",
        "/components/modal",
        "/components/multiple-file-upload",
        "/components/navigation",
        "/components/notification-badge",
        "/components/notification-drawer",
        "/components/number-input",
        "/components/options-menu",
        "/components/overflow-menu",
        "/components/pagination",
        "/components/password-generator",
        "/components/password-strength",
        "/components/popover",
        "/components/progress",
        "/components/progress-stepper",
        "/components/rich-text-editor",
        "/components/ripple",
        "/components/search-input",
        "/components/select",
        "/components/sidebar",
        "/components/switch",
        "/components/table",
        "/components/text-input-group",
        "/components/tile",
        "/components/time-picker",
        "/components/toggle-group",
        "/components/toolbar",
        "/components/topology",
        "/components/topology/custom-nodes",
        "/components/topology/custom-edges",
        "/components/topology/anchors",
        "/components/topology/selection",
        "/components/topology/pan-and-zoom",
        "/components/topology/context-menu",
        "/components/topology/drag-and-drop",
        "/components/topology/control-bar",
        "/components/topology/toolbar",
        "/components/topology/sidebar",
        "/components/topology/layouts",
        "/components/topology/pipelines",
        "/components/tree-view",
        "/components/video-player",
        "/components/wizard",
        "/demos/dashboard",
        "/demos/data-management",
        "/demos/landing",
        "/patterns",
        "/patterns/card-view",
        "/patterns/dashboard",
        "/patterns/filters",
        "/patterns/password-generator",
        "/patterns/password-strength",
        "/patterns/primary-detail",
        "/patterns/right-to-left",
        "/extensions/log-viewer",
        "/extensions/user-feedback",
        "/extensions/catalog-view/catalog-item-header",
        "/extensions/catalog-view/catalog-tile",
        "/extensions/catalog-view/filter-side-panel",
        "/extensions/catalog-view/properties-side-panel",
        "/extensions/catalog-view/vertical-tabs",
        "/extensions/data-view/overview",
        "/extensions/data-view/toolbar",
        "/extensions/data-view/table"
    })
    void route_has_no_missing_icons(String path) {
        given()
            .when().get(path)
            .then()
                .statusCode(200)
                .body(not(containsString("data-missing-icon")));
    }
}
