import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

import java.util.List;

List<Toolbar> toolbars = List.of(
        Toolbar.of("tb-filter-group")
                .section(Section.of(Entry.group(Group.of(
                        Item.toggle(MenuToggle.of("Status").build()),
                        Item.toggle(MenuToggle.of("Risk").build())).variant("filter")))).build(),
        Toolbar.of("tb-filter-group-input")
                .section(Section.of(Entry.group(Group.of(
                        Item.search("Filter by name"),
                        Item.toggle(MenuToggle.of("Status").build()))))).build());

// Template side, with the data in scope:
// {#for t in toolbars}{#include components/navigation/toolbar toolbar=t /}{/for}
