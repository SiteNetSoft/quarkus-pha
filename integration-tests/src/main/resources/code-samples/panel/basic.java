import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-basic")
        .body("Main body content of the panel.").build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
