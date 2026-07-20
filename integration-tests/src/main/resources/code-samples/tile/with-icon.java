import org.sitenetsoft.quarkus.pha.model.*;

Tile tile = Tile.of("With icon").id("tile-icon")
        .icon("fa:plus").body("Icon beside the title.").build();

// Template side, with the data in scope:
// {#include components/data-display/tile tile=tile /}
