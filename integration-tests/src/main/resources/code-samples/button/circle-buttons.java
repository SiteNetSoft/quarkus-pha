import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.icon("fa:circle-plus", "Add (primary circle)").id("btn-circle-primary")
                .variant("primary").asCircle().build(),
        Button.icon("fa:circle-plus", "Add (secondary circle)").id("btn-circle-secondary")
                .variant("secondary").asCircle().build(),
        Button.icon("fa:circle-plus", "Add (tertiary circle)").id("btn-circle-tertiary")
                .variant("tertiary").asCircle().build(),
        Button.icon("fa:circle-plus", "Add (danger circle)").id("btn-circle-danger")
                .variant("danger").asCircle().build(),
        Button.icon("fa:circle-plus", "Add (warning circle)").id("btn-circle-warning")
                .variant("warning").asCircle().build(),
        Button.icon("fa:circle-plus", "Add (link circle)").id("btn-circle-link")
                .variant("link").asCircle().build(),
        Button.icon("fa:copy", "Copy (control circle)").id("btn-circle-control")
                .variant("control").asCircle().build(),
        Button.icon("fa:xmark", "Remove (plain circle)").id("btn-circle-plain")
                .variant("plain").asCircle().build(),
        Button.icon("fa:bell", "Notifications, unread (stateful circle)").id("btn-circle-stateful-unread")
                .variant("stateful").state("unread").asCircle().build(),
        Button.icon("fa:bell", "Notifications, read (stateful circle)").id("btn-circle-stateful-read")
                .variant("stateful").state("read").asCircle().build(),
        Button.icon("fa:bell", "Notifications, attention (stateful circle)").id("btn-circle-stateful-attention")
                .variant("stateful").state("attention").asCircle().build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
