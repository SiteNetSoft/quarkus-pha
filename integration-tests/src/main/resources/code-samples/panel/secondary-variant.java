import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-secondary").secondary()
        .header("<strong>Secondary panel</strong>")
        .body("The <code class=\"ws-code\">.secondary()</code> builder call applies"
                + " <code class=\"ws-code\">pf-m-secondary</code> — uses the PF v6 secondary background"
                + " token, useful for nested panels or aside content.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
