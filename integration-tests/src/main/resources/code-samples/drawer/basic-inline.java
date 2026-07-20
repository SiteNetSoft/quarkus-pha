import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-basic-inline").inline().style("min-height: 240px")
        .toggle("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\"><code class=\"ws-code\">pf-m-inline</code> — the panel"
                + " shares space with the content instead of overlapping it; no backdrop semantics.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
