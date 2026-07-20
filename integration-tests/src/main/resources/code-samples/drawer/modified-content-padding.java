import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-modified-content-padding")
        .style("min-height: 240px").toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">Content body with"
                + " <code class=\"ws-code\">pf-m-padding</code> — drawer content bodies have no"
                + " padding by default.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
