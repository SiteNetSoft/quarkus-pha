import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-resizable-bottom").panelBottom()
        .resizable(200, 100).style("height: 320px").toggle("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">Resizable bottom panel — the splitter is horizontal"
                + " and arrow keys resize vertically.</p>")
        .head("<span>Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
