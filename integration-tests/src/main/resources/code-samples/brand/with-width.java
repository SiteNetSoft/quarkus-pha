import org.sitenetsoft.quarkus.pha.model.*;

Brand brand = Brand.of("/web/images/quarkus-pha-logo.svg", "Quarkus PHA logo")
        .id("brand-width").width("200px").build();

// Template side, with the data in scope:
// {#include components/data-display/brand brand=brand /}
