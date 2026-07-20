import org.sitenetsoft.quarkus.pha.model.*;

Alert alert = Alert.of("Deployment complete").variant("success")
        .description("Your application is live. Review the deployment or roll back if something looks wrong.")
        .actionLink("View deployment", "#").actionLink("Roll back", "#")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/alert alert=alert /}
