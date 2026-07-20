import org.sitenetsoft.quarkus.pha.model.*;

Masthead masthead = Masthead.of("mh-inline").displayInline()
        .toggle("Global navigation").logoText("PHA Showcase")
        .content("Brand and content sit inline (side by side) — pf-m-display-inline.").build();

// Template side, with the data in scope:
// {#include components/navigation/masthead masthead=masthead /}
