import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-basic").style("min-height: 240px")
        .toggleTextSwap("Open drawer", "Close drawer")
        .contentText("<p style=\"margin-top: 1rem\">Main page content. Click the button to slide"
                + " the drawer panel in from the right.</p>")
        .panelSecondary().head("<h2>Panel title</h2>")
        .panelBody("<p>Drawer panel content — details, edit form, related items.</p>", true, false)
        .build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
