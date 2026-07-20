import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Icon> icons = List.of(
        Icon.of("fa:circle-check").size("2xl").label("Solid check").build(),
        Icon.of("fa-regular:envelope").size("2xl").label("Regular envelope").build(),
        Icon.of("fa-brands:github").size("2xl").label("GitHub").build(),
        Icon.of("pficon:cluster").size("2xl").label("Cluster").build());

// Template side, with the data in scope:
// {#for i in icons}{#include components/icon iconModel=i /}{/for}
