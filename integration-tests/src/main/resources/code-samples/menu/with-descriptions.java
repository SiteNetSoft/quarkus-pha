import org.sitenetsoft.quarkus.pha.model.*;

Menu menu = Menu.builder()
        .id("mn-descriptions").style("max-width: 280px")
        .item(MenuItem.of("Edit").description("Change the name or labels."))
        .item(MenuItem.of("Duplicate").description("Create a copy in this project."))
        .item(MenuItem.of("Delete").description("Permanently remove the resource.").asDanger())
        .build();

// Template side, with `menu` in the template data:
// {#include components/navigation/menu menu=menu /}
