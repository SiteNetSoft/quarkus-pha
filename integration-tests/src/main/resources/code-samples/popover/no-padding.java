import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-no-padding")
        .trigger("Show no-padding popover").chromeStyle("position: relative; display: inline-block")
        .wrapperStyle("position: absolute; left: 100%; top: 50%; transform: translateY(-50%);"
                + " width: 320px; z-index: 300")
        .position("right").noPadding()
        .ariaLabel("Popover with no padding").describedByBody()
        .body("This popover has no padding itself. It is intended for content that has its own"
                + " padding and would otherwise sit too far from the popover edges.").build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
