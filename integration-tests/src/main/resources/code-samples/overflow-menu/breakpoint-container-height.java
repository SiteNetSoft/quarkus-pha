import org.sitenetsoft.quarkus.pha.model.*;

OverflowMenu overflowMenu = OverflowMenu.of("om-breakpoint-height")
        .vertical().collapsible()
        .buttonGroup(Button.of("Primary").variant("primary").build(),
                Button.of("Secondary").variant("secondary").build(),
                Button.of("Tertiary").variant("tertiary").build())
        .build();

// Template side, with the data in scope:
// {#include components/navigation/overflow-menu overflowMenu=overflowMenu /}
