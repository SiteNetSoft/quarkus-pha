import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-modified-panel-padding")
        .style("min-height: 240px").toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">The panel body opts out of its default padding"
                + " with <code class=\"ws-code\">pf-m-no-padding</code>.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>")
        .panelBody("Drawer panel body with no padding", false, true).build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
