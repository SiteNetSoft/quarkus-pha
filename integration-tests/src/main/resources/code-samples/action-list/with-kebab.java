import org.sitenetsoft.quarkus.pha.model.*;

ActionList actionList = ActionList.builder()
        .group(ActionList.group(
                ActionList.item(Button.of("Next").variant("primary").build()),
                ActionList.item(Button.of("Back").variant("secondary").build()),
                ActionList.item(MenuToggle.of("").asPlain().icon("fa:ellipsis-vertical")
                        .ariaLabel("More actions").build())))
        .build();

// Template side, with the data in scope:
// {#include components/actions/action-list actionList=actionList /}
