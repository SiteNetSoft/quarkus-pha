import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-icons").style("max-width: 240px")
        .item(MenuItem.of("From git").icon("fa:code"))
        .item(MenuItem.of("Container image").icon("fa:cube"))
        .item(MenuItem.of("Docker file").icon("fa:file-code"))
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
