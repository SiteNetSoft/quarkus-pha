import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-no-header-footer").size("md")
        .trigger("Open header/footer-less modal")
        .ariaLabel("Modal without header or footer")
        .body("<p>Only a body — no header, no footer. The close button still floats in the corner.</p>")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
