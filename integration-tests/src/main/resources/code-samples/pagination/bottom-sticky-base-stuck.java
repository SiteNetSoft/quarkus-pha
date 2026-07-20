import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-bottom-sticky-base-stuck", "Pagination nav - bottom sticky with base and stuck example")
        .asBottom().modifiers("pf-m-sticky-base").stickyScroll().build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
