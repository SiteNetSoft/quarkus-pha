import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.ToggleGroup.Item;

ToggleGroup toggleGroup = ToggleGroup.of("tg-icons", "Alignment").stateVar("align")
        .item(Item.icon("fa:align-left", "Align left").key("left"))
        .item(Item.icon("fa:align-center", "Align center").key("center"))
        .item(Item.icon("fa:align-right", "Align right").key("right")).build();

// Template side, with the data in scope:
// {#include components/forms/toggle-group toggleGroup=toggleGroup /}
