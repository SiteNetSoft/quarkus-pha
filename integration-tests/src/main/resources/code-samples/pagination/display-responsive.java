import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-display-responsive", "Pagination nav - responsive display example")
        .modifiers("pf-m-display-summary pf-m-display-full-on-lg pf-m-display-summary-on-xl pf-m-display-full-on-2xl")
        .build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
