import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

import java.util.List;

List<Toolbar> toolbars = List.of(
        Toolbar.of("tb-color-default")
                .section(Section.of(Entry.item(Item.button(Button.of("Default background").variant("secondary").build())))).build(),
        Toolbar.of("tb-color-primary").variant("primary")
                .section(Section.of(Entry.item(Item.button(Button.of("Primary background").variant("secondary").build())))).build(),
        Toolbar.of("tb-color-secondary").variant("secondary")
                .section(Section.of(Entry.item(Item.button(Button.of("Secondary background").variant("secondary").build())))).build(),
        Toolbar.of("tb-color-none").noBackground()
                .section(Section.of(Entry.item(Item.button(Button.of("No background").variant("secondary").build())))).build());

// Template side, with the data in scope:
// {#for t in toolbars}{#include components/navigation/toolbar toolbar=t /}{/for}
