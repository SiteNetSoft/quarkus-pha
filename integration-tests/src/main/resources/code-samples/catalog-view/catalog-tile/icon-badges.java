import org.sitenetsoft.quarkus.pha.model.*;

CatalogTile catalogTile = CatalogTile
            .of("Istio").id("ct-istio")
            .iconClass("pf-v6-c-icon fas fa-diagram-project")
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-certificate", "Certified"))
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-star", "Featured partner"))
            .vendor("CNCF")
            .description("Service mesh for microservices — traffic management, security, and observability.")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-tile catalogTile=catalogTile /}
