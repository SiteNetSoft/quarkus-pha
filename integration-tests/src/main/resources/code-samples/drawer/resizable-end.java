import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-resizable-end").resizable(500, 150)
        .style("min-height: 240px").toggle("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">Drag the splitter (or focus it and use the arrow"
                + " keys) to resize the panel. Minimum size 150px, default 500px.</p>")
        .head("<span>Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
