import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-resizable-inline").inline().resizable(500, 150)
        .style("min-height: 240px").toggle("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">Resizable panel in an inline drawer.</p>")
        .head("<span>Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
