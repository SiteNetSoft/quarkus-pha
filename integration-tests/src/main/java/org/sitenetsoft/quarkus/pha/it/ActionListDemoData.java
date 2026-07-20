package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.ActionList;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.MenuToggle;

/**
 * Demo data for the action-list examples — every example on
 * /components/action-list is populated from one of these ActionList models,
 * reusing the Button and MenuToggle models for the actions themselves.
 * Globals so the standalone example route (which renders templates without
 * data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/action-list/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class ActionListDemoData {

    public static ActionList demoAlSingleGroup = ActionList.builder()
            .group(ActionList.group(
                    ActionList.item(Button.of("Next").variant("primary").build()),
                    ActionList.item(Button.of("Back").variant("secondary").build())))
            .build();

    public static ActionList demoAlMultipleGroups = ActionList.builder()
            .group(ActionList.group(
                    ActionList.item(Button.of("Next").variant("primary").build()),
                    ActionList.item(Button.of("Back").variant("secondary").build())))
            .group(ActionList.group(
                    ActionList.item(Button.of("Submit").variant("primary").build()),
                    ActionList.item(Button.of("Cancel").variant("link").build())))
            .build();

    public static ActionList demoAlIconsList = ActionList.builder().iconList()
            .group(ActionList.group(
                    ActionList.item(Button.icon("fa:pen-to-square", "Edit").variant("plain").build()),
                    ActionList.item(Button.icon("fa:copy", "Clone").variant("plain").build()),
                    ActionList.item(Button.icon("fa:trash", "Delete").variant("plain").build())))
            .build();

    public static ActionList demoAlIconsGroup = ActionList.builder()
            .group(ActionList.iconGroup(
                    ActionList.item(Button.icon("fa:pen-to-square", "Edit").variant("plain").build()),
                    ActionList.item(Button.icon("fa:copy", "Clone").variant("plain").build())))
            .group(ActionList.group(
                    ActionList.item(Button.of("Save").variant("primary").build()),
                    ActionList.item(Button.of("Cancel").variant("link").build())))
            .build();

    public static ActionList demoAlVertical = ActionList.builder().vertical()
            .group(ActionList.group(
                    ActionList.item(Button.of("Save").variant("primary").build()),
                    ActionList.item(Button.of("Cancel").variant("link").build())))
            .build();

    public static ActionList demoAlWithKebab = ActionList.builder()
            .group(ActionList.group(
                    ActionList.item(Button.of("Next").variant("primary").build()),
                    ActionList.item(Button.of("Back").variant("secondary").build()),
                    ActionList.item(MenuToggle.of("").asPlain().icon("fa:ellipsis-vertical")
                            .ariaLabel("More actions").build())))
            .build();

    public static ActionList demoAlCancelForm = ActionList.builder()
            .group(ActionList.group(
                    ActionList.item(Button.of("Save").variant("primary").build()),
                    ActionList.item(Button.of("Cancel").variant("link").build())))
            .build();

    public static ActionList demoAlCancelWizard = ActionList.builder()
            .group(ActionList.group(
                    ActionList.item(Button.of("Next").variant("primary").build()),
                    ActionList.item(Button.of("Back").variant("secondary").build())))
            .group(ActionList.group(
                    ActionList.item(Button.of("Cancel").variant("link").build())))
            .build();
}
