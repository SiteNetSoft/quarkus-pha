import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-static").staticMode().style("min-height: 240px")
        .contentText("<p>pf-m-static — the panel is always shown, no toggle. Useful for split-pane"
                + " layouts.</p>")
        .panelSecondary().panelBody("<p>Static panel — always visible.</p>", true, false).build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
