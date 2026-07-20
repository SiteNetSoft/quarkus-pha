import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Alert> alerts = List.of(
        Alert.of("Success alert").id("al-group-static-1").variant("success").asInline().build(),
        Alert.of("Info alert").id("al-group-static-2").variant("info").asInline().build(),
        Alert.of("Warning alert").id("al-group-static-3").variant("warning").asInline().build());

// Template side, with the data in scope:
// {#for a in alerts}{#include components/feedback/alert alert=a /}{/for}
