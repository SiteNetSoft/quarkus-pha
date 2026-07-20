import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-action-group-plain")
        .section(Section.of(Entry.group(Group.of(
                Item.button(Button.icon("fa:table-columns", "Column view").variant("plain").build()),
                Item.button(Button.icon("fa:expand", "Expand").variant("plain").build()),
                Item.button(Button.icon("fa:gear", "Settings").variant("plain").build()))
                .modifiers("pf-m-action-group-plain"))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
