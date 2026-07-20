import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Avatar> avatars = List.of(
        Avatar.silhouette("Red avatar").color("red").build(),
        Avatar.silhouette("Orange-red avatar").color("orangered").build(),
        Avatar.silhouette("Orange avatar").color("orange").build(),
        Avatar.silhouette("Yellow avatar").color("yellow").build(),
        Avatar.silhouette("Green avatar").color("green").build(),
        Avatar.silhouette("Teal avatar").color("teal").build(),
        Avatar.silhouette("Blue avatar").color("blue").build(),
        Avatar.silhouette("Purple avatar").color("purple").build(),
        Avatar.silhouette("Gray avatar").color("gray").build());

// Template side, with the data in scope:
// {#for a in avatars}{#include components/data-display/avatar avatar=a /}{/for}
