import org.sitenetsoft.quarkus.pha.model.*;

Badge badge = Badge.of("3").id("badge-sr").variant("unread")
        .screenReaderText("unread notifications").build();

// Template side, with the data in scope:
// {#include components/data-display/badge badge=badge /}
