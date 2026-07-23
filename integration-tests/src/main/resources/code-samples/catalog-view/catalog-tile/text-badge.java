import org.sitenetsoft.quarkus.pha.model.*;

CatalogTile catalogTile = CatalogTile
            .of("Camel").id("ct-community")
            .iconClass("pf-v6-c-icon fas fa-cubes")
            .badge(CatalogTile.Badge.text("Community"))
            .vendor("Apache")
            .description("Integration framework with hundreds of connectors — route, transform, and mediate between systems.")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-tile catalogTile=catalogTile /}
