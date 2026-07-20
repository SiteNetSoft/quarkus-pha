import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("pn-pill").pill().style("max-width: 400px")
        .body("<code class=\"ws-code\">pf-m-pill</code> — rounded pill styling on the panel.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
