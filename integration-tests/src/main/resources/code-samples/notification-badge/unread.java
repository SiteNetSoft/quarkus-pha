import org.sitenetsoft.quarkus.pha.model.*;

NotificationBadge notificationBadge = NotificationBadge
        .of("nb-unread", "3 unread notifications").variant("unread").count("3").build();

// Template side, with the data in scope:
// {#include components/feedback/notification-badge notificationBadge=notificationBadge /}
