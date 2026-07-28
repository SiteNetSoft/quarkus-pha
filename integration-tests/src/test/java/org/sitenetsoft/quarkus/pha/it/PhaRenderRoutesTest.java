package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/** The VDP-flow preview endpoint: pha: URI + JSON payload → rendered PatternFly HTML. */
@QuarkusTest
class PhaRenderRoutesTest {

    @Test
    void renders_a_menu_from_uri_and_payload() {
        given().contentType("application/json")
            .queryParam("template", "pha:components/navigation/menu")
            .queryParam("model", "menu")
            .body("{\"id\": \"mn-http\", \"items\": [{\"text\": \"Edit\"}, {\"text\": \"Delete\", \"danger\": true}]}")
            .when().post("/api/pha/render")
            .then().statusCode(200)
            .body(containsString("mn-http"))
            .body(containsString("pf-m-danger"));
    }

    @Test
    void rejects_bad_uris_payloads_and_models() {
        given().contentType("application/json").body("{}")
            .queryParam("template", "pha:../secrets").queryParam("model", "menu")
            .when().post("/api/pha/render").then().statusCode(400);
        given().contentType("application/json").body("{}")
            .queryParam("template", "pha:components/navigation/menu").queryParam("model", "nope")
            .when().post("/api/pha/render").then().statusCode(400);
        given().contentType("application/json").body("{\"id\": \"mn-x\"}")
            .queryParam("template", "pha:components/navigation/menu").queryParam("model", "menu")
            .when().post("/api/pha/render").then().statusCode(400); // empty menu fails build()
        given().contentType("application/json").body("{\"value\": \"7\"}")
            .queryParam("template", "pha:components/definitely/missing").queryParam("model", "badge")
            .when().post("/api/pha/render").then().statusCode(404);
    }
}
