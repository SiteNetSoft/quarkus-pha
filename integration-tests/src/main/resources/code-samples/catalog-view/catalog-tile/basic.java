import org.sitenetsoft.quarkus.pha.model.*;

CatalogTile quarkus = CatalogTile
            .of("Quarkus").id("ct-quarkus")
            .vendor("Red Hat")
            .description("Supersonic, subatomic Java framework — Kubernetes-native, GraalVM-friendly, built for the cloud era.")
            .iconClass("pf-v6-c-icon fas fa-bolt")
            .build();

CatalogTile htmx = CatalogTile
            .of("HTMX").id("ct-htmx").featured()
            .iconClass("pf-v6-c-icon fas fa-arrow-right-arrow-left")
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-certificate", "Certified"))
            .vendor("Big Sky Software")
            .description("High-power tools for HTML — modern UX with minimal JavaScript. Adds AJAX, CSS Transitions, WebSockets and SSE directly to HTML, using attributes.")
            .build();

CatalogTile alpine = CatalogTile
            .of("Alpine.js").id("ct-alpine")
            .vendor("Caleb Porzio")
            .description("Your new, lightweight JavaScript framework — Tailwind for JavaScript. 15kB and reactive.")
            .iconClass("pf-v6-c-icon fas fa-mountain")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-tile catalogTile=quarkus /}  (one include per tile)
