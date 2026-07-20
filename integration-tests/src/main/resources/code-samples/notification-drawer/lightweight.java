import org.sitenetsoft.quarkus.pha.model.*;

import org.sitenetsoft.quarkus.pha.model.NotificationDrawer.Item;

NotificationDrawer notificationDrawer = NotificationDrawer.of("nd-lightweight")
        .ariaLabel("Lightweight notifications").style("max-width: 480px")
        .item(Item.of("info", "Build completed")
                .description("Pipeline #1247 finished in 3m 12s.").timestamp("2 minutes ago"))
        .item(Item.of("success", "Deploy succeeded").read()
                .description("v2.18.3 rolled out to all replicas.").timestamp("1 hour ago"))
        .build();

// Template side, with the data in scope:
// {#include components/feedback/notification-drawer notificationDrawer=notificationDrawer /}
