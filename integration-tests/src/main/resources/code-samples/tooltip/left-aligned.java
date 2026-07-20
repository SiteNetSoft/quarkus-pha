import org.sitenetsoft.quarkus.pha.model.*;

Tooltip tooltip = Tooltip.of("tt-left-aligned").position("top").textAlignLeft()
        .trigger(Button.of("Left aligned text").variant("secondary").build())
        .tip("This tooltip wraps onto several lines because it carries a lot of text, and"
                + " pf-m-text-align-left keeps every line ragged-right instead of centered.").build();

// Template side, with the data in scope:
// {#include components/feedback/tooltip tooltip=tooltip /}
