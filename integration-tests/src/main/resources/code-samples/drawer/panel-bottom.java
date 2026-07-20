import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-panel-bottom").panelBottom().style("height: 280px")
        .toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">The panel slides up from the bottom —"
                + " <code class=\"ws-code\">pf-m-panel-bottom</code> on the drawer root. The drawer"
                + " needs an explicit height for a bottom panel to be visible.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
