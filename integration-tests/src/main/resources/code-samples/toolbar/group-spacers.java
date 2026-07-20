import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Entry;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Group;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Item;
import org.sitenetsoft.quarkus.pha.model.Toolbar.Section;

Toolbar toolbar = Toolbar.of("tb-group-spacers")
        .section(Section.of(
                Entry.group(Group.of(Item.button(Button.of("gap-none 1").variant("secondary").build()), Item.button(Button.of("gap-none 2").variant("secondary").build()))
                        .columnGap("none").style("outline: 1px dashed var(--pf-t--global--border--color--default)")),
                Entry.group(Group.of(Item.button(Button.of("gap-2xl 1").variant("secondary").build()), Item.button(Button.of("gap-2xl 2").variant("secondary").build()))
                        .columnGap("2xl").style("outline: 1px dashed var(--pf-t--global--border--color--default)"))))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/toolbar toolbar=toolbar /}
