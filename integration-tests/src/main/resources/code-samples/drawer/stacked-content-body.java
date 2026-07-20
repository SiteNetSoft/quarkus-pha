import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-stacked-content-body").style("min-height: 280px")
        .firstBodyBare().toggleFocus("Toggle drawer")
        .contentBody("content-body with padding", true, false)
        .contentBody("content-body", false, false)
        .head("<h3 class=\"pf-v6-c-title pf-m-2xl\" :tabindex=\"expanded ? '0' : '-1'\" x-ref=\"focus\">\n"
                + "  Drawer panel title\n</h3>")
        .panelBody("Drawer panel body with no padding", false, true)
        .panelBody("Drawer panel body", false, false).build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
