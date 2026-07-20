import org.sitenetsoft.quarkus.pha.model.*;

Brand brand = Brand.of("/web/images/quarkus-pha-logo.svg", "Quarkus PHA logo")
        .id("brand-height").height("36px").build();

// Template side, with the data in scope:
// {#include components/data-display/brand brand=brand /}
