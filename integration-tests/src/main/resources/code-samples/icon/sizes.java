import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Icon> icons = List.of(
        Icon.of("fa:circle-info").size("sm").build(),
        Icon.of("fa:circle-info").size("md").build(),
        Icon.of("fa:circle-info").size("lg").build(),
        Icon.of("fa:circle-info").size("xl").build(),
        Icon.of("fa:circle-info").size("2xl").build(),
        Icon.of("fa:circle-info").size("3xl").build());

// Template side, with the data in scope:
// {#for i in icons}{#include components/icon iconModel=i /}{/for}
