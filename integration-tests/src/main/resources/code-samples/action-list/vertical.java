import org.sitenetsoft.quarkus.pha.model.*;

ActionList actionList = ActionList.builder().vertical()
        .group(ActionList.group(
                ActionList.item(Button.of("Save").variant("primary").build()),
                ActionList.item(Button.of("Cancel").variant("link").build())))
        .build();

// Template side, with the data in scope:
// {#include components/actions/action-list actionList=actionList /}
