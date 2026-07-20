import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-no-body")
        .main("Direct child of panel-main — no panel-main-body wrapper."
                + " The container provides no extra padding here.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
