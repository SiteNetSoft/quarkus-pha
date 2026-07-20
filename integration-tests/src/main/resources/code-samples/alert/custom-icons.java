import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Alert> alerts = List.of(
        Alert.of("Success alert with a custom rocket icon").id("al-custom-icon-1").variant("success")
                .customIcon("fa:rocket").build(),
        Alert.of("Warning alert with a custom clock icon").id("al-custom-icon-2").variant("warning")
                .customIcon("fa:clock").build());

// Template side, with the data in scope:
// {#for a in alerts}{#include components/feedback/alert alert=a /}{/for}
