package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Path("/")
public class HelloResource {

    @Inject
    Template hello;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        List<Map<String, String>> components = List.of(
            Map.of("id", "about-modal",
                   "name", "About modal",
                   "image", "/web/images/about-modal.png",
                   "href", "/components/about-modal"),
            Map.of("id", "accordion",
                   "name", "Accordion",
                   "image", "/web/images/accordion.png",
                   "href", "/components/accordion")
        );

        return hello.data("applicationName", "quarkus-pha")
                     .data("components", components);
    }
}
