import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-one-page", "Pagination nav - one page example").total(8).build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
