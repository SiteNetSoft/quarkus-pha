import org.sitenetsoft.quarkus.pha.model.*;

OverflowMenu overflowMenu = OverflowMenu.of("om-group-types")
        .buttonGroup(Button.of("Save").variant("primary").build(),
                Button.of("Cancel").variant("secondary").build())
        .iconButtonGroup(Button.icon("fa:clone", "Clone").variant("plain").build(),
                Button.icon("fa:arrows-rotate", "Sync").variant("plain").build(),
                Button.icon("fa:pen-to-square", "Edit").variant("plain").build())
        .build();

// Template side, with the data in scope:
// {#include components/navigation/overflow-menu overflowMenu=overflowMenu /}
