import org.sitenetsoft.quarkus.pha.model.*;

Alert alert = Alert.of("Expandable info alert").id("al-expandable").variant("info")
        .description("The description and action links stay hidden until the alert is expanded.")
        .asExpandable()
        .actionLink("View details")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/alert alert=alert /}
