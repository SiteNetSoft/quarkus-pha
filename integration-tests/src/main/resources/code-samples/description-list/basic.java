import org.sitenetsoft.quarkus.pha.model.*;

DescriptionList list = DescriptionList.builder()
        .id("dl-basic")
        .group("Name", "Jane Doe")
        .group("Email", "jane@example.com")
        .group("Role", "Senior engineer")
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/description-list list=list /}
