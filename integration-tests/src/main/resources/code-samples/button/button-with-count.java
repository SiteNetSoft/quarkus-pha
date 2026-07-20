import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.of("Notifications").id("btn-count-unread").variant("secondary").count(5).build(),
        Button.of("Messages").id("btn-count-read").variant("secondary").count(12).countRead().build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
