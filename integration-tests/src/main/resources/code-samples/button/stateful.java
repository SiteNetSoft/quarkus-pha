import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.icon("fa:bell", "Notifications, all read").id("btn-stateful-read")
                .variant("stateful").state("read").build(),
        Button.icon("fa:bell", "Notifications, unread").id("btn-stateful-unread")
                .variant("stateful").state("unread").build(),
        Button.icon("fa:bell", "Notifications, needs attention").id("btn-stateful-attention")
                .variant("stateful").state("attention").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
