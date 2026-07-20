import org.sitenetsoft.quarkus.pha.model.*;

Tooltip tooltip = Tooltip.of("tt-basic").position("top")
        .trigger(Button.of("Hover or focus me").variant("secondary").build())
        .tip("I'm a tooltip").build();

// Template side, with the data in scope:
// {#include components/feedback/tooltip tooltip=tooltip /}
