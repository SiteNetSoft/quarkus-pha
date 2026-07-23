import org.sitenetsoft.quarkus.pha.model.*;

CatalogItemHeader catalogItemHeader = CatalogItemHeader
            .of("Quarkus pha")
            .vendorHtml("SiteNetSoft")
            .iconClass("pf-v6-c-icon fas fa-box")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-item-header catalogItemHeader=catalogItemHeader /}
