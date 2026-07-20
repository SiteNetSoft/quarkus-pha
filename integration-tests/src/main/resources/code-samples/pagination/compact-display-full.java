import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-compact-display-full", "Pagination nav - compact display full example")
        .asCompact().modifiers("pf-m-display-full").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
