import org.sitenetsoft.quarkus.pha.model.*;

Masthead masthead = Masthead.of("mh-responsive")
        .displayStack().modifiers("pf-m-display-inline-on-lg")
        .toggle("Global navigation").logoText("PHA Showcase")
        .content("Stacked by default, switching to inline from the <code>lg</code> breakpoint up —"
                + " pf-m-display-stack pf-m-display-inline-on-lg. Resize the window to see it switch.")
        .build();

// Template side, with the data in scope:
// {#include components/navigation/masthead masthead=masthead /}
