import org.sitenetsoft.quarkus.pha.model.*;

CatalogTile catalogTile = CatalogTile
            .of("Kubernetes").id("ct-kubernetes")
            .vendor("CNCF")
            .description("Production-grade container orchestration — automated deployment, scaling, and management.")
            .iconClass("pf-v6-c-icon fas fa-network-wired")
            .footer("Available in the Operator catalog")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-tile catalogTile=catalogTile /}
