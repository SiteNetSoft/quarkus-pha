import org.sitenetsoft.quarkus.pha.model.*;

Tooltip tooltip = Tooltip.of("tt-long").position("bottom")
        .trigger(Button.of("Long tooltip").variant("secondary").build())
        .tip("This tooltip carries a lot of text. PatternFly caps the bubble at 18.75rem wide,"
                + " so the content wraps onto multiple lines rather than stretching across the page.").build();

// Template side, with the data in scope:
// {#include components/feedback/tooltip tooltip=tooltip /}
