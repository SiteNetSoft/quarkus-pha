import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-breakpoint").style("min-height: 240px")
        .toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">The panel width is set with"
                + " <code class=\"ws-code\">pf-m-width-33</code> — width modifiers (25/33/50/66/75/100)"
                + " also come in -on-lg / -on-xl forms.</p>")
        .panelWidths("pf-m-width-33").head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
