import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-no-padding")
        .modifiers("pf-m-no-padding")
        .section(Section.of(
                Entry.item(Item.text("Item")),
                Entry.item(Item.text("Item")),
                Entry.item(Item.text("Item")),
                Entry.dividerHr(),
                Entry.group(Group.of(Item.text("Item"), Item.text("Item")))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
