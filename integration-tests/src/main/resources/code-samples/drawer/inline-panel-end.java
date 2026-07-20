import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-inline-panel-end").inline().style("min-height: 240px")
        .toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">Inline drawer, panel at the end —"
                + " <code class=\"ws-code\">pf-m-inline</code>.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
