import org.sitenetsoft.quarkus.pha.model.*;

Brand brand = Brand.of("/web/images/quarkus-pha-icon.svg", "Quarkus PHA logo")
        .id("brand-picture")
        .sources("<source media=\"(min-width: 768px)\" srcset=\"/web/images/quarkus-pha-logo.svg\" />")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/brand brand=brand /}
