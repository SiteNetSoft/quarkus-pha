import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Tooltip> tooltips = List.of(
        Tooltip.of("tt-top-left").position("top-left")
                .trigger(Button.of("Top left").variant("secondary").build()).tip("Top left tooltip").build(),
        Tooltip.of("tt-top-right").position("top-right")
                .trigger(Button.of("Top right").variant("secondary").build()).tip("Top right tooltip").build(),
        Tooltip.of("tt-bottom-left").position("bottom-left")
                .trigger(Button.of("Bottom left").variant("secondary").build()).tip("Bottom left tooltip").build(),
        Tooltip.of("tt-bottom-right").position("bottom-right")
                .trigger(Button.of("Bottom right").variant("secondary").build()).tip("Bottom right tooltip").build(),
        Tooltip.of("tt-left-top").position("left-top")
                .trigger(Button.of("Left top").variant("secondary").build()).tip("Left top tooltip").build(),
        Tooltip.of("tt-left-bottom").position("left-bottom")
                .trigger(Button.of("Left bottom").variant("secondary").build()).tip("Left bottom tooltip").build(),
        Tooltip.of("tt-right-top").position("right-top")
                .trigger(Button.of("Right top").variant("secondary").build()).tip("Right top tooltip").build(),
        Tooltip.of("tt-right-bottom").position("right-bottom")
                .trigger(Button.of("Right bottom").variant("secondary").build()).tip("Right bottom tooltip").build());

// Template side, with the data in scope:
// {#for t in tooltips}{#include components/feedback/tooltip tooltip=t /}{/for}
