import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Button> buttons = List.of(
        Button.hamburger("Open menu").id("btn-hamburger-default").ariaExpanded("false").build(),
        Button.hamburger("Open menu").id("btn-hamburger-expand").hamburgerVariant("expand")
                .ariaExpanded("false").build(),
        Button.hamburger("Close menu").id("btn-hamburger-collapse").hamburgerVariant("collapse")
                .ariaExpanded("true").build());

// Template side, with the data in scope:
// {#for b in buttons}{#include components/actions/button btn=b /}{/for}
