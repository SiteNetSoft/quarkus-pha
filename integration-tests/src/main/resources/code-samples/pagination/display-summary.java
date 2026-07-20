import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-display-summary", "Pagination nav - display summary example")
        .modifiers("pf-m-display-summary").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
