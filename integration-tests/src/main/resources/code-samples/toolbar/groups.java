import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-groups")
        .section(Section.of(
                Entry.group(Group.of(
                        Item.toggle(MenuToggle.of("Status").build()),
                        Item.toggle(MenuToggle.of("Risk").build())).variant("filter")),
                Entry.group(Group.of(
                        Item.button(Button.icon("fa:clone", "Clone").variant("plain").build()),
                        Item.button(Button.icon("fa:arrows-rotate", "Sync").variant("plain").build()),
                        Item.button(Button.icon("fa:pen-to-square", "Edit").variant("plain").build()))),
                Entry.group(Group.of(
                        Item.button(Button.of("Create").variant("primary").build()),
                        Item.button(Button.of("Secondary").variant("secondary").build())).alignEnd())))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
