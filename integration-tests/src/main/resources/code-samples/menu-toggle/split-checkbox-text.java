import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.split("mt-split-check-text")
        .checkbox("Select all").checkLabel("10 selected").build();

// Template side, with the data in scope:
// {#include components/navigation/menu-toggle toggle=toggle /}
