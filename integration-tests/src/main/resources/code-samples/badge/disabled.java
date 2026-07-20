import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Badge> badges = List.of(
        Badge.of("7").id("badge-disabled-7").variant("disabled").build(),
        Badge.of("24").id("badge-disabled-24").variant("disabled").build(),
        Badge.of("999+").id("badge-disabled-999").variant("disabled").build());

// Template side, with the data in scope:
// {#for b in badges}{#include components/data-display/badge badge=b /}{/for}
