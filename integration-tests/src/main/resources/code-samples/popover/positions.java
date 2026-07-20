import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

// One Popover per PF position token; the wrapper style anchors it to the trigger.
Popover top = Popover.of("po-pos-top").trigger("Top").position("top")
        .chromeStyle("position: relative; display: inline-block")
        .wrapperStyle("position: absolute; bottom: 100%; left: 50%; transform: translateX(-50%); width: 300px; z-index: 300")
        .title("Popover header").dismissable()
        .body("The pf-m-top modifier places the arrow accordingly.")
        .footerText("Popover footer").build();
// ...repeat for right, bottom, left, left-top, left-bottom, bottom-left, bottom-right
// (diagonal tokens map to pf-m-left-top etc.; adjust the wrapper anchor per side).

// Template side, with the data in scope:
// {#for p in popovers}{#include components/feedback/popover popover=p /}{/for}
