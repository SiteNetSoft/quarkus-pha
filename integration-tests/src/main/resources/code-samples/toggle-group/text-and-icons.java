import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.ToggleGroup.Item;

ToggleGroup toggleGroup = ToggleGroup.of("tg-text-icons", "Toggle group")
        .item(Item.of("Copy").withIcon("fa:copy"))
        .item(Item.of("Paste").withIcon("fa:clipboard"))
        .item(Item.of("Print").withIcon("fa:print")).build();

// Template side, with the data in scope:
// {#include components/forms/toggle-group toggleGroup=toggleGroup /}
