import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-bottom-static", "Pagination nav - bottom static example")
        .asBottom().modifiers("pf-m-static").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
