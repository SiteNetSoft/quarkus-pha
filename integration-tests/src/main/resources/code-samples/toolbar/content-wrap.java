import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-content-wrap")
        .section(Section.of(
                Entry.item(Item.button(Button.of("Wrapping action 1").variant("secondary").build())),
                Entry.item(Item.button(Button.of("Wrapping action 2").variant("secondary").build())),
                Entry.item(Item.button(Button.of("Wrapping action 3").variant("secondary").build())),
                Entry.item(Item.button(Button.of("Wrapping action 4").variant("secondary").build())),
                Entry.item(Item.button(Button.of("Wrapping action 5").variant("secondary").build())),
                Entry.item(Item.button(Button.of("Wrapping action 6").variant("secondary").build())))
                .modifiers("pf-m-wrap"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
