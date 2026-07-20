import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-vertical").vertical()
        .section(Section.of(
                Entry.item(Item.button(Button.icon("fa:table-cells-large", "Grid view").variant("plain").build())),
                Entry.item(Item.button(Button.icon("fa:filter", "Filter").variant("plain").build())),
                Entry.item(Item.button(Button.icon("fa:gear", "Settings").variant("plain").build()))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
