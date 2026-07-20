import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.ToggleGroup.Item;

ToggleGroup toggleGroup = ToggleGroup.of("tg-multi", "Filters").multiVar("filters")
        .item(Item.of("Bold").key("bold"))
        .item(Item.of("Italic").key("italic").pressed())
        .item(Item.of("Underline").key("underline")).build();

// Template side, with the data in scope:
// {#include components/forms/toggle-group toggleGroup=toggleGroup /}
