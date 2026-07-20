import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("").id("mt-plain").asPlain()
        .ariaLabel("More actions").build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
