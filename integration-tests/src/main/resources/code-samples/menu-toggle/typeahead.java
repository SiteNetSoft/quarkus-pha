import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.typeahead("mt-typeahead", "Type to filter").build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
