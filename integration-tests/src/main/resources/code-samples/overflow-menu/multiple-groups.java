import org.sitenetsoft.quarkus.pha.model.*;

OverflowMenu overflowMenu = OverflowMenu.of("om-multiple-groups")
        .buttonGroup(Button.of("Create").variant("primary").build(),
                Button.of("Import").variant("secondary").build())
        .buttonGroup(Button.of("Export").variant("secondary").build(),
                Button.of("Share").variant("secondary").build())
        .iconButtonGroup(Button.icon("fa:gear", "Settings").variant("plain").build())
        .build();

// Template side, with the data in scope:
// {#include components/navigation/overflow-menu overflowMenu=overflowMenu /}
