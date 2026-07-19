import org.sitenetsoft.quarkus.pha.model.*;

DescriptionList list = DescriptionList.builder()
        .id("dl-fill").fillColumns()
        .group("Status", "Active")
        .group("Created", "2026-05-20")
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/description-list list=list /}
