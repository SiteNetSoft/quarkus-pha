package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.NotificationDrawer;
import org.sitenetsoft.quarkus.pha.model.NotificationDrawer.Item;

/**
 * Demo data for the notification-drawer examples — all examples on
 * /components/notification-drawer are populated from these models. Globals so
 * the standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in
 * resources/code-samples/notification-drawer/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class NotificationDrawerDemoData {

    private static final String DEMO_STYLE =
            "max-width: 480px; border: 1px solid var(--pf-t--global--border--color--default)";

    public static NotificationDrawer demoNdBasic = NotificationDrawer.of("nd-basic")
            .header("3 unread").style(DEMO_STYLE)
            .item(Item.of("info", "Build completed")
                    .description("Pipeline #1247 finished in 3m 12s.").timestamp("2 minutes ago"))
            .item(Item.of("warning", "Memory threshold exceeded")
                    .description("Node prod-3 is at 92% memory usage.").timestamp("5 minutes ago"))
            .item(Item.of("success", "Deploy succeeded").read()
                    .description("v2.18.3 rolled out to all replicas.").timestamp("1 hour ago"))
            .build();

    public static NotificationDrawer demoNdGroups = NotificationDrawer.of("nd-groups")
            .header("3 unread").ariaLabel("Grouped notifications").style(DEMO_STYLE)
            .group("Production alerts", true, "2",
                    Item.of("warning", "Memory threshold exceeded")
                            .description("Node prod-3 is at 92% memory usage.").timestamp("5 minutes ago"),
                    Item.of("info", "Pod restarted")
                            .description("api-7d9f restarted twice in 10 minutes.").timestamp("12 minutes ago"))
            .group("Staging alerts", false, "1",
                    Item.of("success", "Deploy succeeded").read()
                            .description("v2.18.3 rolled out to staging.").timestamp("1 hour ago"))
            .build();

    public static NotificationDrawer demoNdLightweight = NotificationDrawer.of("nd-lightweight")
            .ariaLabel("Lightweight notifications").style("max-width: 480px")
            .item(Item.of("info", "Build completed")
                    .description("Pipeline #1247 finished in 3m 12s.").timestamp("2 minutes ago"))
            .item(Item.of("success", "Deploy succeeded").read()
                    .description("v2.18.3 rolled out to all replicas.").timestamp("1 hour ago"))
            .build();
}
