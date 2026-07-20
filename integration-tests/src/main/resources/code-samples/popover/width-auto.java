import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-width-auto")
        .trigger("Show auto-width popover").widthAuto()
        .wrapperStyle("position: absolute; bottom: 100%; left: 50%; transform: translateX(-50%);"
                + " width: auto; z-index: 300")
        .ariaLabel("Width auto").body("Sizes to its content.").build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
