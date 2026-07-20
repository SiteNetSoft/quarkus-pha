package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Brand;

/**
 * Demo data for the brand examples — the examples on /components/brand are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/brand/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class BrandDemoData {

    public static Brand demoBrandBasic = Brand.of("/web/images/quarkus-pha-logo.svg", "Quarkus PHA logo")
            .id("brand-basic").build();

    public static Brand demoBrandHeight = Brand.of("/web/images/quarkus-pha-logo.svg", "Quarkus PHA logo")
            .id("brand-height").height("36px").build();

    public static Brand demoBrandWidth = Brand.of("/web/images/quarkus-pha-logo.svg", "Quarkus PHA logo")
            .id("brand-width").width("200px").build();

    public static Brand demoBrandPicture = Brand.of("/web/images/quarkus-pha-icon.svg", "Quarkus PHA logo")
            .id("brand-picture")
            .sources("<source media=\"(min-width: 768px)\" srcset=\"/web/images/quarkus-pha-logo.svg\" />")
            .build();
}
