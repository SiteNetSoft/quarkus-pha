import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-header")
        .header("<strong>Panel header</strong>")
        .body("Main body sits below the header. PF v6 doesn't auto-insert a divider —"
                + " add one if the visual separation matters.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
