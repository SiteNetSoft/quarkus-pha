import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Avatar> avatars = List.of(
        Avatar.initials("C", "Avatar with initial C").id("avatar-initials").bordered().build(),
        Avatar.initials("C", "Red avatar with initial C").color("red").build(),
        Avatar.initials("C", "Orange-red avatar with initial C").color("orangered").build(),
        Avatar.initials("C", "Orange avatar with initial C").color("orange").build(),
        Avatar.initials("C", "Yellow avatar with initial C").color("yellow").build(),
        Avatar.initials("C", "Green avatar with initial C").color("green").build(),
        Avatar.initials("C", "Teal avatar with initial C").color("teal").build(),
        Avatar.initials("C", "Blue avatar with initial C").color("blue").build(),
        Avatar.initials("C", "Purple avatar with initial C").color("purple").build(),
        Avatar.initials("C", "Gray avatar with initial C").color("gray").build());

// Template side, with the data in scope:
// {#for a in avatars}{#include components/data-display/avatar avatar=a /}{/for}
