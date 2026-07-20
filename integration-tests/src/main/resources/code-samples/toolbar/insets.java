import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-insets")
        .modifiers("pf-m-inset-md pf-m-inset-xl-on-lg")
        .section(Section.of(
                Entry.item(Item.button(Button.of("Inset md").variant("secondary").build())),
                Entry.item(Item.button(Button.of("Action 2").variant("secondary").build()))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
