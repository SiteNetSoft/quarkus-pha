import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-offset", "Pagination nav - offset example").startPage(3).build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
