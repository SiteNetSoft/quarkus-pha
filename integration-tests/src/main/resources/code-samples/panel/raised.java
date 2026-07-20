import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-raised").raised()
        .header("<strong>Raised panel</strong>")
        .body("The <code class=\"ws-code\">.raised()</code> builder call applies"
                + " <code class=\"ws-code\">pf-m-raised</code> — adds a drop shadow that lifts"
                + " the panel off the background.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
