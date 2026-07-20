import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-indeterminate", "Pagination nav - indeterminate example")
        .asIndeterminate().build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
