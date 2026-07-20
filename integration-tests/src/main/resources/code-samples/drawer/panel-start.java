import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-panel-start").panelLeft().style("min-height: 240px")
        .toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">The panel slides in from the start (left) edge —"
                + " <code class=\"ws-code\">pf-m-panel-left</code> on the drawer root.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
