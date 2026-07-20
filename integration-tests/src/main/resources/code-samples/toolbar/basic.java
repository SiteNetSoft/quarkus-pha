import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-basic")
        .section(Section.of(
                Entry.group(Group.of(
                        Item.search("Search"),
                        Item.button(Button.of("Filter").variant("secondary").build()))),
                Entry.group(Group.of(
                        Item.button(Button.of("Create").variant("primary").build()),
                        Item.button(Button.icon("fa:ellipsis-vertical", "More actions").variant("plain").build()))
                        .alignEnd())))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
