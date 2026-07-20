import org.sitenetsoft.quarkus.pha.model.*;

import org.sitenetsoft.quarkus.pha.model.NotificationDrawer.Item;

NotificationDrawer notificationDrawer = NotificationDrawer.of("nd-basic")
        .header("3 unread").style("max-width: 480px; border: 1px solid var(--pf-t--global--border--color--default)")
        .item(Item.of("info", "Build completed")
                .description("Pipeline #1247 finished in 3m 12s.").timestamp("2 minutes ago"))
        .item(Item.of("warning", "Memory threshold exceeded")
                .description("Node prod-3 is at 92% memory usage.").timestamp("5 minutes ago"))
        .item(Item.of("success", "Deploy succeeded").read()
                .description("v2.18.3 rolled out to all replicas.").timestamp("1 hour ago"))
        .build();

// Template side, with the data in scope:
// {#include components/feedback/notification-drawer notificationDrawer=notificationDrawer /}
