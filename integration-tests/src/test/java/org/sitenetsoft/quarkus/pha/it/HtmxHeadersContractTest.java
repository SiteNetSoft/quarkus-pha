package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * HTMX endpoint response-header contract tests.
 *
 * Smoke tests cover that the *body* is a fragment. These tests cover
 * the *headers*: that the endpoints behave as well-formed HTMX swap
 * targets and don't accidentally leak anything that would break the
 * client (a stray HX-Redirect would reload the page; a Set-Cookie on
 * an idempotent GET would mean session leak).
 *
 * Each pinned behavior is something a refactor could silently break.
 * Aspirational additions (e.g. explicit Cache-Control) belong in their
 * own follow-up — this file pins what's true today.
 */
@QuarkusTest
class HtmxHeadersContractTest {

    // ── Content-Type ──────────────────────────────────────────────────────

    @ParameterizedTest
    @ValueSource(strings = {
        "/api/htmx/search?q=alpha",
        "/api/htmx/items?page=0",
        "/api/htmx/load-more?page=0",
        "/api/htmx/modal-content/1"
    })
    void content_type_is_text_html_utf8(String url) {
        given()
            .when().get(url)
            .then()
                .statusCode(200)
                .header("Content-Type", containsString("text/html"))
                .header("Content-Type", containsString("UTF-8"));
    }

    // ── No accidental client-state mutations on idempotent GETs ──────────

    @ParameterizedTest
    @ValueSource(strings = {
        "/api/htmx/search?q=alpha",
        "/api/htmx/items?page=0",
        "/api/htmx/load-more?page=0",
        "/api/htmx/modal-content/1"
    })
    void no_set_cookie_on_idempotent_get(String url) {
        given()
            .when().get(url)
            .then()
                .statusCode(200)
                .header("Set-Cookie", nullValue());
    }

    // ── No HTMX-control headers leaking by default ───────────────────────
    // HX-Redirect would force a full-page redirect, breaking swap UX.
    // HX-Refresh would force a full reload. HX-Push-Url would change the
    // URL bar. None should be set on plain content endpoints.

    @ParameterizedTest
    @ValueSource(strings = {
        "/api/htmx/search?q=alpha",
        "/api/htmx/items?page=0",
        "/api/htmx/load-more?page=0",
        "/api/htmx/modal-content/1"
    })
    void no_unsolicited_hx_redirect(String url) {
        given()
            .when().get(url)
            .then()
                .header("HX-Redirect", nullValue())
                .header("HX-Refresh", nullValue())
                .header("HX-Push-Url", nullValue());
    }

    // ── Empty / out-of-range pagination still 200, not 5xx ───────────────

    @Test
    void items_page_past_end_returns_empty_200() {
        given()
            .queryParam("page", 999)
            .when().get("/api/htmx/items")
            .then()
                .statusCode(200)
                .header("Content-Type", containsString("text/html"))
                .body(equalToIgnoringCase(""));
    }

    @Test
    void load_more_page_past_end_returns_empty_200() {
        given()
            .queryParam("page", 999)
            .when().get("/api/htmx/load-more")
            .then()
                .statusCode(200)
                .body(equalToIgnoringCase(""));
    }

    // ── Search with no matches returns 200 + a non-empty "no results" fragment.
    //   Different from items/load-more's empty body — pin this distinction.

    @Test
    void search_with_no_matches_returns_no_results_fragment() {
        given()
            .queryParam("q", "definitely-not-anything-xyz")
            .when().get("/api/htmx/search")
            .then()
                .statusCode(200)
                .body(containsString("No results found"))
                .body(not(containsString("<!DOCTYPE")));
    }

    // ── Path-param XSS guard: the response must never contain an executable
    //   <script> tag built from path-param input. Whether JAX-RS percent-decodes
    //   the param or leaves it raw, the final body must be safe.

    @Test
    void modal_content_does_not_emit_unescaped_script_from_path_param() {
        given()
            .when().get("/api/htmx/modal-content/foo%3Cscript%3Ealert(1)%3C%2Fscript%3E")
            .then()
                .statusCode(200)
                .body(not(containsString("<script>")))
                .body(not(containsString("</script>")));
    }
}
