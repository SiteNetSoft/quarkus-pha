import org.sitenetsoft.quarkus.pha.model.*;

CatalogTile catalogTile = CatalogTile
            .of("OpenShift").id("ct-openshift").href("#")
            .vendor("Red Hat")
            .description("Enterprise Kubernetes platform — click this tile to open the catalog entry.")
            .iconClass("pf-v6-c-icon fas fa-dharmachakra")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-tile catalogTile=catalogTile /}
