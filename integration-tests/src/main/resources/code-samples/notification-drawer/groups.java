import org.sitenetsoft.quarkus.pha.model.*;

import org.sitenetsoft.quarkus.pha.model.NotificationDrawer.Item;

NotificationDrawer notificationDrawer = NotificationDrawer.of("nd-groups")
        .header("3 unread").ariaLabel("Grouped notifications").style("max-width: 480px; border: 1px solid var(--pf-t--global--border--color--default)")
        .group("Production alerts", true, "2",
                Item.of("warning", "Memory threshold exceeded")
                        .description("Node prod-3 is at 92% memory usage.").timestamp("5 minutes ago"),
                Item.of("info", "Pod restarted")
                        .description("api-7d9f restarted twice in 10 minutes.").timestamp("12 minutes ago"))
        .group("Staging alerts", false, "1",
                Item.of("success", "Deploy succeeded").read()
                        .description("v2.18.3 rolled out to staging.").timestamp("1 hour ago"))
        .build();

// Template side, with the data in scope:
// {#include components/feedback/notification-drawer notificationDrawer=notificationDrawer /}
