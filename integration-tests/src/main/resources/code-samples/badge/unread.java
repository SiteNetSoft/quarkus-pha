import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Badge> badges = List.of(
        Badge.of("7").id("badge-unread-7").variant("unread").build(),
        Badge.of("24").id("badge-unread-24").variant("unread").build(),
        Badge.of("999+").id("badge-unread-999").variant("unread").build());

// Template side, with the data in scope:
// {#for b in badges}{#include components/data-display/badge badge=b /}{/for}
