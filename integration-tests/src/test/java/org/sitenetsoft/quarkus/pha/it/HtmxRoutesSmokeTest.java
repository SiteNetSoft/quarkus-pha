package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

/**
 * Smoke tests for HTMX endpoints — server-driven UI fragments under /api/htmx.
 *
 * HTMX endpoints return *partials*, not full pages: no &lt;!DOCTYPE&gt;,
 * no &lt;html&gt;, no &lt;body&gt;, just the element to be swapped in.
 * If any of these contracts break, every HTMX-driven component on the
 * client breaks silently — these tests catch that at the server boundary.
 */
@QuarkusTest
class HtmxRoutesSmokeTest {

    @Test
    void search_returns_html_fragment() {
        given()
            .queryParam("q", "alpha")
            .when().get("/api/htmx/search")
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(not(containsString("<!DOCTYPE")))
                .body(not(containsString("<html")))
                .body(not(containsString("<body")))
                .body(containsString("Server Alpha"));
    }

    @Test
    void search_with_empty_query_returns_all_items() {
        given()
            .queryParam("q", "")
            .when().get("/api/htmx/search")
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(containsString("Server Alpha"))
                .body(containsString("Database Gamma"));
    }

    @Test
    void search_with_no_match_still_returns_200() {
        given()
            .queryParam("q", "definitely-not-a-real-thing-xyz")
            .when().get("/api/htmx/search")
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"));
    }

    @Test
    void items_returns_html_fragment() {
        given()
            .when().get("/api/htmx/items")
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(not(containsString("<!DOCTYPE")))
                .body(not(containsString("<html")));
    }

    @Test
    void load_more_returns_html_fragment() {
        given()
            .queryParam("page", 1)
            .when().get("/api/htmx/load-more")
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(not(containsString("<!DOCTYPE")))
                .body(not(containsString("<html")));
    }

    @Test
    void modal_content_returns_html_fragment() {
        given()
            .when().get("/api/htmx/modal-content/1")
            .then()
                .statusCode(200)
                .contentType(containsString("text/html"))
                .body(not(containsString("<!DOCTYPE")))
                .body(not(containsString("<html")));
    }
}
