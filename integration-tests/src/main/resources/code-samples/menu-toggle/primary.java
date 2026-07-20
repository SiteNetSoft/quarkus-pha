import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Choose").id("mt-primary").asPrimary().build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
