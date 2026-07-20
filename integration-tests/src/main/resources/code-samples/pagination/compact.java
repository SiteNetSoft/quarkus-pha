import org.sitenetsoft.quarkus.pha.model.*;

Pagination pagination = Pagination
        .of("pg-compact", "Pagination nav - compact example").asCompact().build();

// Template side, with `pagination` in the template data:
// {#include components/navigation/pagination pagination=pagination /}
