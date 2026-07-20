import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-item-spacers")
        .section(Section.of(
                Entry.item(Item.button(Button.of("Default gap").variant("secondary").build())),
                Entry.item(Item.button(Button.of("gap-none").variant("secondary").build()).gap("none")),
                Entry.item(Item.button(Button.of("gap-xl").variant("secondary").build()).gap("xl")),
                Entry.item(Item.button(Button.of("gap-4xl").variant("secondary").build()).gap("4xl"))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
