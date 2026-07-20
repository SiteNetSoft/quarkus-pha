import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-pill").pill().style("min-height: 280px")
        .toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\"><code class=\"ws-code\">pf-m-pill</code> — the panel"
                + " renders as a detached, rounded &quot;pill&quot; floating over the content.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").panelBody("Drawer panel body", false, false).build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
