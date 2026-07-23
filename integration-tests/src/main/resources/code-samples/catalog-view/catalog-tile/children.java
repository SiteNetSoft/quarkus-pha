import org.sitenetsoft.quarkus.pha.model.*;

CatalogTile catalogTile = CatalogTile
            .of("Qute").id("ct-children").featured()
            .iconClass("pf-v6-c-icon fas fa-feather")
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-certificate", "Certified"))
            .vendor("Quarkus")
            .bodyHtml("""
                    This is a long stretch of arbitrary body content that is not truncated, illustrating how a tile
                    can carry richer content than a plain description. Descriptions normally clamp at three lines
                    (one line with a footer); body content placed directly in the card body has no clamp at all and
                    can run as long as it needs to.""")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/catalog-tile catalogTile=catalogTile /}
