import org.sitenetsoft.quarkus.pha.model.*;

Tile tile = Tile.of("Stacked icon").id("tile-stacked")
        .icon("fa:plus").stacked().body("The icon sits above the title (pf-m-stacked).").build();

// Template side, with the data in scope:
// {#include components/data-display/tile tile=tile /}
