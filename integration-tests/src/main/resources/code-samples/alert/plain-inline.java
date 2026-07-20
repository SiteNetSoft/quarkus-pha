import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Alert> alerts = List.of(
        Alert.of("Plain inline info alert").id("al-plain-info").variant("info").asInline().asPlain().build(),
        Alert.of("Plain inline success alert").id("al-plain-success").variant("success").asInline().asPlain().build(),
        Alert.of("Plain inline danger alert").id("al-plain-danger").variant("danger").asInline().asPlain().build());

// Template side, with the data in scope:
// {#for a in alerts}{#include components/feedback/alert alert=a /}{/for}
