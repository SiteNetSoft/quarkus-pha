import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-display-full", "Pagination nav - display full example")
        .modifiers("pf-m-display-full").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
