import org.sitenetsoft.quarkus.pha.model.*;

ActionList actionList = ActionList.builder().iconList()
        .group(ActionList.group(
                ActionList.item(Button.icon("fa:pen-to-square", "Edit").variant("plain").build()),
                ActionList.item(Button.icon("fa:copy", "Clone").variant("plain").build()),
                ActionList.item(Button.icon("fa:trash", "Delete").variant("plain").build())))
        .build();

// Template side, with the data in scope:
// {#include components/actions/action-list actionList=actionList /}
