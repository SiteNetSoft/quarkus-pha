package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Serves the web app manifest rendered from the extension's Qute template so it
 * carries this application's identity ({@code applicationName} is provided by
 * {@link GlobalTemplateData}). The extension's base layout links it from every page.
 */
@Path("/manifest.json")
public class ManifestResource {

    @Location("manifest.json")
    @Inject
    Template manifest;

    @GET
    @Produces("application/manifest+json")
    public TemplateInstance manifest() {
        return manifest.instance()
            .data("description", "Component showcase for the quarkus-pha extension");
    }
}
