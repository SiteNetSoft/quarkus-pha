import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-generic-container").size("md")
        .trigger("Open generic modal container")
        .ariaLabel("Generic modal container").noClose()
        .genericHtml("<p style=\"padding: 1rem\">\n    The modal box children elements can be"
                + " removed, and the modal serves as a generic container. One use case of this\n"
                + "    is when creating a wizard in a modal. Press <kbd>Escape</kbd> to close"
                + " this one.\n  </p>")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
