import org.sitenetsoft.quarkus.pha.model.*;

ActionList actionList = ActionList.builder()
        .group(ActionList.iconGroup(
                ActionList.item(Button.icon("fa:pen-to-square", "Edit").variant("plain").build()),
                ActionList.item(Button.icon("fa:copy", "Clone").variant("plain").build())))
        .group(ActionList.group(
                ActionList.item(Button.of("Save").variant("primary").build()),
                ActionList.item(Button.of("Cancel").variant("link").build())))
        .build();

// Template side, with the data in scope:
// {#include components/actions/action-list actionList=actionList /}
