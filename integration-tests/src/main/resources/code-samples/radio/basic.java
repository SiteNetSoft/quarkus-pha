import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Radio> items = List.of(
        Radio.of("r-basic-one", "r-basic").label("Option one").checked().build(),
        Radio.of("r-basic-two", "r-basic").label("Option two").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/radio radio=x /}{/for}
