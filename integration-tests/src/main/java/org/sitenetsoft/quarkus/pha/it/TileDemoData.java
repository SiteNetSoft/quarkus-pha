package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Tile;

import java.util.List;

/**
 * Demo data for the tile examples — the examples on /components/tile are
 * populated from these models (single-selection and multiple-selection stay
 * hand-written: selection is live Alpine page state on a deprecated-upstream
 * component). Globals so the standalone example route (which renders templates
 * without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/tile/ served on the example card's Java tab — keep
 * them in sync when editing.
 */
@TemplateGlobal
public class TileDemoData {

    public static List<Tile> demoTilesBasic = List.of(
            Tile.of("Default").id("tile-basic").build(),
            Tile.of("Selected").id("tile-basic-selected").selected().build(),
            Tile.of("Disabled").id("tile-basic-disabled").disabled().build());

    public static Tile demoTileIcon = Tile.of("With icon").id("tile-icon")
            .icon("fa:plus").body("Icon beside the title.").build();

    public static Tile demoTileSubtext = Tile.of("With subtext").id("tile-subtext")
            .body("A short line of supporting text under the title.").build();

    public static Tile demoTileStacked = Tile.of("Stacked icon").id("tile-stacked")
            .icon("fa:plus").stacked().body("The icon sits above the title (pf-m-stacked).").build();

    public static Tile demoTileLarge = Tile.of("Large icon").id("tile-large")
            .icon("fa:cloud").stacked().displayLg().body("pf-m-display-lg grows the stacked icon.").build();

    public static Tile demoTileLong = Tile.of("Long subtext").id("tile-long")
            .body("This tile carries a much longer line of supporting text. It wraps onto several"
                    + " lines inside the tile body instead of stretching the tile wider and wider"
                    + " across the page.").build();
}
