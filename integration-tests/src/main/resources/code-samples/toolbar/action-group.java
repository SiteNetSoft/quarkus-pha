import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-action-group")
        .section(Section.of(Entry.group(Group.of(
                Item.button(Button.of("Primary").variant("primary").build()),
                Item.button(Button.of("Secondary").variant("secondary").build()),
                Item.button(Button.of("Tertiary").variant("tertiary").build()))
                .modifiers("pf-m-action-group"))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
