import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Check> items = List.of(
        Check.of("cb-disabled").label("Disabled, unchecked").disabled().build(),
        Check.of("cb-disabled-checked").label("Disabled, checked").checked().disabled().build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/check check=x /}{/for}
