import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Small toggle").id("mt-small").asSmall().build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
