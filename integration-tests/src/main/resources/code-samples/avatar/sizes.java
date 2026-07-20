import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Avatar> avatars = List.of(
        Avatar.initials("C", "Small red avatar with initial C").id("avatar-sm").color("red").size("sm").build(),
        Avatar.initials("C", "Medium red avatar with initial C").id("avatar-md").color("red").size("md").build(),
        Avatar.initials("C", "Large red avatar with initial C").id("avatar-lg").color("red").size("lg").build(),
        Avatar.initials("C", "Extra large red avatar with initial C").id("avatar-xl").color("red").size("xl").build());

// Template side, with the data in scope:
// {#for a in avatars}{#include components/data-display/avatar avatar=a /}{/for}
