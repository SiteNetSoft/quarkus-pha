import org.sitenetsoft.quarkus.pha.model.*;

Masthead masthead = Masthead.of("mh-insets")
        .modifiers("pf-m-inset-sm pf-m-inset-md-on-md pf-m-inset-lg-on-lg pf-m-inset-xl-on-xl")
        .style("outline: 1px dashed var(--pf-t--global--border--color--default, #ccc)")
        .toggle("Global navigation").logoText("PHA Showcase")
        .content("Inset padding scales up with the breakpoint (sm → md → lg → xl). The dashed outline"
                + " marks the masthead edge so the growing inset is visible.")
        .build();

// Template side, with the data in scope:
// {#include components/navigation/masthead masthead=masthead /}
