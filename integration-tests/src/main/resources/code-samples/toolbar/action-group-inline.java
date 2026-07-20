import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-action-group-inline")
        .section(Section.of(Entry.group(Group.of(
                Item.text("6 filters applied"),
                Item.button(Button.of("Clear all filters").variant("link").asInline().build()),
                Item.button(Button.of("Save all filters").variant("link").asInline().build()))
                .modifiers("pf-m-action-group-inline"))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
