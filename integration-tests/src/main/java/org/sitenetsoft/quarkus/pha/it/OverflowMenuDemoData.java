package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.OverflowMenu;

/**
 * Demo data for the overflow-menu examples — the examples on
 * /components/overflow-menu are populated from these models; groups hold
 * composed Button models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/overflow-menu/ served on the example card's Java
 * tab — keep them in sync when editing.
 */
@TemplateGlobal
public class OverflowMenuDemoData {

    public static OverflowMenu demoOmBasic = OverflowMenu.of("om-basic")
            .buttonGroup(Button.of("Refresh").variant("secondary").build(),
                    Button.of("Save").variant("primary").build())
            .build();

    public static OverflowMenu demoOmGroupTypes = OverflowMenu.of("om-group-types")
            .buttonGroup(Button.of("Save").variant("primary").build(),
                    Button.of("Cancel").variant("secondary").build())
            .iconButtonGroup(Button.icon("fa:clone", "Clone").variant("plain").build(),
                    Button.icon("fa:arrows-rotate", "Sync").variant("plain").build(),
                    Button.icon("fa:pen-to-square", "Edit").variant("plain").build())
            .build();

    public static OverflowMenu demoOmMultipleGroups = OverflowMenu.of("om-multiple-groups")
            .buttonGroup(Button.of("Create").variant("primary").build(),
                    Button.of("Import").variant("secondary").build())
            .buttonGroup(Button.of("Export").variant("secondary").build(),
                    Button.of("Share").variant("secondary").build())
            .iconButtonGroup(Button.icon("fa:gear", "Settings").variant("plain").build())
            .build();

    public static OverflowMenu demoOmPersistent = OverflowMenu.of("om-persistent")
            .buttonGroup(Button.of("Primary action").variant("primary").build())
            .persistentMenu("Edit", "Duplicate", "Archive")
            .build();

    public static OverflowMenu demoOmVertical = OverflowMenu.of("om-vertical").vertical()
            .buttonGroup(Button.of("Primary").variant("primary").build(),
                    Button.of("Secondary").variant("secondary").build(),
                    Button.of("Tertiary").variant("tertiary").build())
            .build();

    public static OverflowMenu demoOmBpWidth = OverflowMenu.of("om-breakpoint-width").collapsible()
            .buttonGroup(Button.of("Primary").variant("primary").build(),
                    Button.of("Secondary").variant("secondary").build(),
                    Button.of("Tertiary").variant("tertiary").build())
            .build();

    public static OverflowMenu demoOmBpHeight = OverflowMenu.of("om-breakpoint-height")
            .vertical().collapsible()
            .buttonGroup(Button.of("Primary").variant("primary").build(),
                    Button.of("Secondary").variant("secondary").build(),
                    Button.of("Tertiary").variant("tertiary").build())
            .build();
}
