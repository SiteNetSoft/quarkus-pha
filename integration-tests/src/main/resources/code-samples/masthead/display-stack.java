import org.sitenetsoft.quarkus.pha.model.*;

Masthead masthead = Masthead.of("mh-stack").displayStack()
        .brandHtml("<strong>Display stack — brand on top</strong>")
        .content("Content row sits below the brand row when display-stack is active.").build();

// Template side, with the data in scope:
// {#include components/navigation/masthead masthead=masthead /}
