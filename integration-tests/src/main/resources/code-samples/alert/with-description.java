import org.sitenetsoft.quarkus.pha.model.*;

Alert alert = Alert.of("With description").id("al-desc").variant("info")
        .description("A second line of explanatory text under the title. Use this to add detail beyond the headline.")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/alert alert=alert /}
