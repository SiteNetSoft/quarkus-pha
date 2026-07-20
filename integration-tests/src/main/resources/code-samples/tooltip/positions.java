import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Tooltip> tooltips = List.of(
        Tooltip.of("tt-top").position("top")
                .trigger(Button.of("Top").variant("secondary").build()).tip("Top tooltip").build(),
        Tooltip.of("tt-right").position("right")
                .trigger(Button.of("Right").variant("secondary").build()).tip("Right tooltip").build(),
        Tooltip.of("tt-bottom").position("bottom")
                .trigger(Button.of("Bottom").variant("secondary").build()).tip("Bottom tooltip").build(),
        Tooltip.of("tt-left").position("left")
                .trigger(Button.of("Left").variant("secondary").build()).tip("Left tooltip").build());

// Template side, with the data in scope:
// {#for t in tooltips}{#include components/feedback/tooltip tooltip=t /}{/for}
