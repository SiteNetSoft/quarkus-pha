import org.sitenetsoft.quarkus.pha.model.*;

Tile tile = Tile.of("With subtext").id("tile-subtext")
        .body("A short line of supporting text under the title.").build();

// Template side, with the data in scope:
// {#include components/data-display/tile tile=tile /}
