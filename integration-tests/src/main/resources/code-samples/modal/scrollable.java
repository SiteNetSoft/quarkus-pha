import org.sitenetsoft.quarkus.pha.model.*;

Modal modal = Modal.of("mo-scrollable").size("md")
        .trigger("Open scrollable modal")
        .title("Scrollable modal").headingLevel("h2").bodyScrollable()
        .body("<p>Lorem ipsum…</p>".repeat(14)  // 14 filler paragraphs)
        .footerButton("Confirm", "pf-m-primary").build();

// Template side, with the data in scope:
// {#include components/feedback/modal modal=modal /}
