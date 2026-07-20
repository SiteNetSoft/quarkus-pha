import org.sitenetsoft.quarkus.pha.model.*;

OverflowMenu overflowMenu = OverflowMenu.of("om-basic")
        .buttonGroup(Button.of("Refresh").variant("secondary").build(),
                Button.of("Save").variant("primary").build())
        .build();

// Template side, with the data in scope:
// {#include components/navigation/overflow-menu overflowMenu=overflowMenu /}
