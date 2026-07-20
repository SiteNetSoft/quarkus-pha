import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-no-items", "Pagination nav - no items example").total(0).build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
