import org.sitenetsoft.quarkus.pha.model.*;

NotificationBadge notificationBadge = NotificationBadge
        .of("nb-attention", "Critical notifications need attention").variant("attention").count("!").build();

// Template side, with the data in scope:
// {#include components/feedback/notification-badge notificationBadge=notificationBadge /}
