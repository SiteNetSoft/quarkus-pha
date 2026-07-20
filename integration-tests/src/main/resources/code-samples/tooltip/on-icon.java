import org.sitenetsoft.quarkus.pha.model.*;

Tooltip tooltip = Tooltip.of("tt-icon").position("top")
        .trigger(Button.icon("fa:circle-info", "More information").variant("plain").build())
        .tip("More information about this field").build();

// Template side, with the data in scope:
// {#include components/feedback/tooltip tooltip=tooltip /}
