import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.favorite("Add to favorites").id("btn-favorite-off").build(),
        Button.favorite("Remove from favorites").id("btn-favorite-on").asFavorited().build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
