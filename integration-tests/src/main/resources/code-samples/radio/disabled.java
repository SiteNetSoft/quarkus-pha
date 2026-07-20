import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Radio> items = List.of(
        Radio.of("r-dis-one", "r-dis").label("Disabled, unselected").disabled().build(),
        Radio.of("r-dis-two", "r-dis").label("Disabled, selected").checked().disabled().build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/radio radio=x /}{/for}
