import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Icon> icons = List.of(
        Icon.of("fa:circle-info").size("3xl").iconSize("sm").label("3xl container, sm glyph").build(),
        Icon.of("fa:circle-info").size("3xl").iconSize("md").label("3xl container, md glyph").build(),
        Icon.of("fa:circle-info").size("3xl").iconSize("lg").label("3xl container, lg glyph").build(),
        Icon.of("fa:circle-info").size("3xl").iconSize("xl").label("3xl container, xl glyph").build(),
        Icon.of("fa:circle-info").size("3xl").iconSize("3xl").label("3xl container, 3xl glyph").build());

// Template side, with the data in scope:
// {#for i in icons}{#include components/icon iconModel=i /}{/for}
