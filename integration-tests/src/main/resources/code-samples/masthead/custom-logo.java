import org.sitenetsoft.quarkus.pha.model.*;

Masthead masthead = Masthead.of("mh-custom-logo")
        .toggle("Global navigation")
        .logoImages("#", "PHA Showcase home", "/web/images/quarkus-pha-logo.svg",
                "/web/images/quarkus-pha-logo-dark.svg", "Quarkus PHA", "36px")
        .content("Content").build();

// Template side, with the data in scope:
// {#include components/navigation/masthead masthead=masthead /}
