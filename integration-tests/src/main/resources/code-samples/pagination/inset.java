import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-inset", "Pagination nav - inset example")
        .modifiers("pf-m-inset-none pf-m-inset-md-on-md pf-m-inset-2xl-on-lg").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
