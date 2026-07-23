import org.sitenetsoft.quarkus.pha.model.*;

CatalogItemHeader catalogItemHeader = CatalogItemHeader
            .of("Quarkus pha").id("cih-vendor")
            .iconClass("pf-v6-c-icon fas fa-box")
            .vendorHtml("provided by <a href=\"https://github.com/SiteNetSoft\" style=\"text-decoration: underline\">SiteNetSoft</a>")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-item-header catalogItemHeader=catalogItemHeader /}
