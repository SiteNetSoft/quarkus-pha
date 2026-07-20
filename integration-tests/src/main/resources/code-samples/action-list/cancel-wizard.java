import org.sitenetsoft.quarkus.pha.model.*;

ActionList actionList = ActionList.builder()
        .group(ActionList.group(
                ActionList.item(Button.of("Next").variant("primary").build()),
                ActionList.item(Button.of("Back").variant("secondary").build())))
        .group(ActionList.group(
                ActionList.item(Button.of("Cancel").variant("link").build())))
        .build();

// Template side, with the data in scope:
// {#include components/actions/action-list actionList=actionList /}
