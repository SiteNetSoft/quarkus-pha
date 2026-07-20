import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-inline-panel-start").inline().panelLeft()
        .style("min-height: 240px").toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">Inline drawer with the panel at the start (left) —"
                + " <code class=\"ws-code\">pf-m-inline pf-m-panel-left</code>.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
