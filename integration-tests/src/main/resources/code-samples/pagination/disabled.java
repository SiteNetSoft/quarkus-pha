import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-disabled", "Pagination nav - disabled example").asDisabled().build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
