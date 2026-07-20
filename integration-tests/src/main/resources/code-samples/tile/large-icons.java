import org.sitenetsoft.quarkus.pha.model.*;

Tile tile = Tile.of("Large icon").id("tile-large")
        .icon("fa:cloud").stacked().displayLg().body("pf-m-display-lg grows the stacked icon.").build();

// Template side, with the data in scope:
// {#include components/data-display/tile tile=tile /}
