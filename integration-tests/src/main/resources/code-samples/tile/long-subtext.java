import org.sitenetsoft.quarkus.pha.model.*;

Tile tile = Tile.of("Long subtext").id("tile-long")
        .body("This tile carries a much longer line of supporting text. It wraps onto several"
                + " lines inside the tile body instead of stretching the tile wider and wider"
                + " across the page.").build();

// Template side, with the data in scope:
// {#include components/data-display/tile tile=tile /}
