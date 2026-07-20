import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Alert> alerts = List.of(
        Alert.of("Success alert with everything").id("al-variation-full").variant("success")
                .description("A description paragraph adds detail below the title.")
                .asClosable()
                .actionLink("View details").actionLink("Ignore")
                .build(),
        Alert.of("Success alert with description").id("al-variation-desc").variant("success")
                .description("Title plus description only.").build(),
        Alert.of("Success alert title only").id("al-variation-plain-title").variant("success").build());

// Template side, with the data in scope:
// {#for a in alerts}{#include components/feedback/alert alert=a /}{/for}
