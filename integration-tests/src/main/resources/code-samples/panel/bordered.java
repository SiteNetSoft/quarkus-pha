import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-bordered").bordered()
        .header("<strong>Bordered panel</strong>")
        .body("The <code class=\"ws-code\">.bordered()</code> builder call applies"
                + " <code class=\"ws-code\">pf-m-bordered</code> — outlines the panel with a 1px border.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
