import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-sticky").sticky()
        .section(Section.of(
                Entry.item(Item.search("Search")),
                Entry.item(Item.button(Button.of("Action").variant("secondary").build()))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
