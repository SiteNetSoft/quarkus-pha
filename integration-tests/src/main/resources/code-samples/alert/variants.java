import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Alert> alerts = List.of(
        Alert.of("Info alert").id("al-info").variant("info").build(),
        Alert.of("Success alert").id("al-success").variant("success").build(),
        Alert.of("Warning alert").id("al-warning").variant("warning").build(),
        Alert.of("Danger alert").id("al-danger").variant("danger").build(),
        Alert.of("Custom alert").id("al-custom").variant("custom").build());

// Template side, with the data in scope:
// {#for a in alerts}{#include components/feedback/alert alert=a /}{/for}
