import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-bottom-sticky", "Pagination nav - bottom sticky example")
        .asBottom().modifiers("pf-m-sticky").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
