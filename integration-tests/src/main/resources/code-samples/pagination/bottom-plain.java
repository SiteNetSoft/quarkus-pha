import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-bottom-plain", "Pagination nav - bottom plain example")
        .asBottom().modifiers("pf-m-plain").build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
