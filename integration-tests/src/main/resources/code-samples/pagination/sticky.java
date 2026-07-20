import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-sticky", "Pagination nav - top sticky example")
        .modifiers("pf-m-sticky").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
