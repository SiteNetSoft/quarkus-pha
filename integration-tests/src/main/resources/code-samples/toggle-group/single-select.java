import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.ToggleGroup.Item;

ToggleGroup toggleGroup = ToggleGroup.of("tg-single", "View mode")
        .stateVar("selected").selectedKey("list")
        .item(Item.of("List").key("list"))
        .item(Item.of("Grid").key("grid"))
        .item(Item.of("Table").key("table")).build();

// Template side, with the data in scope:
// {#include components/forms/toggle-group toggleGroup=toggleGroup /}
