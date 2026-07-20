import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Tile> tiles = List.of(
        Tile.of("Default").id("tile-basic").build(),
        Tile.of("Selected").id("tile-basic-selected").selected().build(),
        Tile.of("Disabled").id("tile-basic-disabled").disabled().build());

// Template side, with the data in scope:
// {#for t in tiles}{#include components/data-display/tile tile=t /}{/for}
