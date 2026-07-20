import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-panel-end").style("min-height: 240px")
        .toggleFocus("Toggle drawer")
        .contentText("<p style=\"margin-top: 1rem\">Lorem ipsum dolor sit amet, consectetur adipiscing"
                + " elit. Phasellus pretium est a porttitor vehicula. Quisque vel commodo urna. Morbi"
                + " mattis rutrum ante, id vehicula ex accumsan ut.</p>")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
