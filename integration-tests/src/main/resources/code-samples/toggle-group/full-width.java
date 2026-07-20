import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.ToggleGroup.Item;

ToggleGroup toggleGroup = ToggleGroup.of("tg-full-width", "Toggle group").fill()
        .item(Item.of("Development")).item(Item.of("Staging")).item(Item.of("Production")).build();

// Template side, with the data in scope:
// {#include components/forms/toggle-group toggleGroup=toggleGroup /}
