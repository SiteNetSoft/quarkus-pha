import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-basic").size("md").trigger("Open modal")
        .title("Are you sure?").headingLevel("h2")
        .body("<p>This action removes the project and all its associated data. It cannot be undone.</p>")
        .footerButton("Delete", "danger")
        .footerButton("Cancel", "link").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
