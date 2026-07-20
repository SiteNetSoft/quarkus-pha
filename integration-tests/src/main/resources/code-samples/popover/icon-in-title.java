import org.sitenetsoft.quarkus.pha.model.*;

Popover popover = Popover.of("po-icon-in-title")
        .trigger("Show icon-title popover")
        .title("Title with icon").titleIcon("fa:rocket").dismissable()
        .body("The icon sits in the __title-icon slot before the title text.").build();

// Template side, with the data in scope:
// {#include components/feedback/popover popover=popover /}
