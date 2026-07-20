import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Badge> badges = List.of(
        Badge.of("7").id("badge-read-7").variant("read").build(),
        Badge.of("24").id("badge-read-24").variant("read").build(),
        Badge.of("999+").id("badge-read-999").variant("read").build());

// Template side, with the data in scope:
// {#for b in badges}{#include components/data-display/badge badge=b /}{/for}
