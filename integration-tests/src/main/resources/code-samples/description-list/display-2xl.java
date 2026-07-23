import org.sitenetsoft.quarkus.pha.model.*;

DescriptionList list = DescriptionList.builder()
        .id("dl-display-2xl").ariaLabel("Description list 2xl display size")
        .display2xl().groups(groups)
        .build();

// Template side, with the data in scope:
// {#include components/data-display/description-list list=list /}
