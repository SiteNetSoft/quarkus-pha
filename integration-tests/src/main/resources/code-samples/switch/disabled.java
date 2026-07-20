import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Switch> switches = List.of(
        Switch.of("sw-dis-off").label("Disabled off").disabled().build(),
        Switch.of("sw-dis-on").label("Disabled on").checked().disabled().build());

// Template side, with the data in scope:
// {#for s in switches}{#include components/forms/switch sw=s /}{/for}
