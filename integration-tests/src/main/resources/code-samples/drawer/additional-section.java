import org.sitenetsoft.quarkus.pha.model.*;

Drawer drawer = Drawer.of("dr-additional-section").style("min-height: 280px")
        .section("drawer-section above the drawer content")
        .toggleFocus("Toggle drawer")
        .contentText("""
                <p style="margin-top: 1rem">
                  A <code class="ws-code">pf-v6-c-drawer__section</code> sits outside <code class="ws-code">__main</code> — above
                  the content and panel.
                </p>""")
        .head("<span :tabindex=\\"expanded ? '0' : '-1'\\" x-ref=\\"focus\\">Drawer panel header</span>").build();

// Template side, with the data in scope:
// {#include components/data-display/drawer drawer=drawer /}
