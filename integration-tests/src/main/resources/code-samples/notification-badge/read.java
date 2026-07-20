import org.sitenetsoft.quarkus.pha.model.*;

NotificationBadge notificationBadge = NotificationBadge
        .of("nb-read", "Notifications, all read").variant("read").build();

// Template side, with the data in scope:
// {#include components/feedback/notification-badge notificationBadge=notificationBadge /}
