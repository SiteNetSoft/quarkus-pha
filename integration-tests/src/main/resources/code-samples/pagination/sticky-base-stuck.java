import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-sticky-base-stuck", "Pagination nav - top sticky with base and stuck example")
        .modifiers("pf-m-sticky-base").stickyScroll().build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
