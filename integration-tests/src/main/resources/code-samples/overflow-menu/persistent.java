import org.sitenetsoft.quarkus.pha.model.*;

OverflowMenu overflowMenu = OverflowMenu.of("om-persistent")
        .buttonGroup(Button.of("Primary action").variant("primary").build())
        .persistentMenu("Edit", "Duplicate", "Archive")
        .build();

// Template side, with the data in scope:
// {#include components/navigation/overflow-menu overflowMenu=overflowMenu /}
