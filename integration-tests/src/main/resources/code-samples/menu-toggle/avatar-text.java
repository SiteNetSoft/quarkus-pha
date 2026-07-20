import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<MenuToggle> toggles = List.of(
        MenuToggle.of("Ned Username").id("mt-avatar").asAvatar().build(),
        MenuToggle.of("Ned Username").id("mt-avatar-expanded").asAvatar().asExpanded().build(),
        MenuToggle.of("Ned Username").id("mt-avatar-disabled").asAvatar().asDisabled().build());

// Template side, with the data in scope:
// {#for t in toggles}{#include components/navigation/menu-toggle toggle=t /}{/for}
