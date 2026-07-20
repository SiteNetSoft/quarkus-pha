import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-width-control")
        .section(Section.of(
                Entry.group(Group.of(
                        Item.text("80px / 10rem on xl")
                                .style("--pf-v6-c-toolbar__item--Width: 80px;"
                                        + " --pf-v6-c-toolbar__item--Width-on-xl: 10rem; " + "outline: 1px dashed var(--pf-t--global--border--color--default)"),
                        Item.text("Item"))),
                Entry.dividerHr(),
                Entry.item(Item.text("Item"))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
