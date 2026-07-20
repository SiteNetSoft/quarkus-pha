import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-dynamic-sticky", "Pagination nav - dynamic sticky example")
        .stickyToggle().build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
