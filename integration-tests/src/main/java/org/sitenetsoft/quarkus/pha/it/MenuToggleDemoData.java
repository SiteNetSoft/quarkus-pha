package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.MenuToggle;

import java.util.List;

/**
 * Demo data for the menu-toggle examples — every example on
 * /components/menu-toggle is populated from these MenuToggle models. Globals
 * so the standalone example route (which renders templates without data) can
 * see them. Each is mirrored by a snippet in
 * resources/code-samples/menu-toggle/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class MenuToggleDemoData {

    public static MenuToggle demoToggleBasic = MenuToggle.of("Actions").id("mt-basic").build();

    public static MenuToggle demoToggleExpanded = MenuToggle.of("Open").id("mt-expanded").asExpanded().build();

    public static MenuToggle demoTogglePrimary = MenuToggle.of("Choose").id("mt-primary").asPrimary().build();

    public static MenuToggle demoTogglePlain = MenuToggle.of("").id("mt-plain").asPlain()
            .ariaLabel("More actions").build();

    public static MenuToggle demoToggleDisabled = MenuToggle.of("Locked").id("mt-disabled").asDisabled().build();

    public static List<MenuToggle> demoToggleVariants = List.of(
            MenuToggle.of("Default").id("mt-variant-default").build(),
            MenuToggle.of("Primary").id("mt-variant-primary").asPrimary().build(),
            MenuToggle.of("Secondary").id("mt-variant-secondary").asSecondary().build(),
            MenuToggle.of("Danger").id("mt-variant-danger").asDanger().build());

    public static List<MenuToggle> demoTogglePlainText = List.of(
            MenuToggle.of("Text").id("mt-plain-text").asPlain().asText()
                    .ariaLabel("Plain menu toggle").build(),
            MenuToggle.of("Text").id("mt-plain-text-expanded").asPlain().asText().asExpanded()
                    .ariaLabel("Expanded plain menu toggle").build(),
            MenuToggle.of("Text").id("mt-plain-text-disabled").asPlain().asText().asDisabled()
                    .ariaLabel("Disabled plain menu toggle").build());

    public static List<MenuToggle> demoTogglePlainCircle = List.of(
            MenuToggle.of("").id("mt-circle").asPlain().asCircle().icon("fa:ellipsis-vertical")
                    .ariaLabel("Plain circle kebab").build(),
            MenuToggle.of("").id("mt-circle-expanded").asPlain().asCircle().asExpanded()
                    .icon("fa:ellipsis-vertical").ariaLabel("Expanded plain circle kebab").build(),
            MenuToggle.of("").id("mt-circle-disabled").asPlain().asCircle().asDisabled()
                    .icon("fa:ellipsis-vertical").ariaLabel("Disabled plain circle kebab").build());

    public static MenuToggle demoToggleSmall = MenuToggle.of("Small toggle").id("mt-small").asSmall().build();

    public static MenuToggle demoToggleFullHeight = MenuToggle.of("Full height").id("mt-full-height")
            .asFullHeight().build();

    public static MenuToggle demoToggleFullWidth = MenuToggle.of("Full width").id("mt-full-width")
            .asFullWidth().build();

    public static MenuToggle demoToggleInForm = MenuToggle.of("Select an owner").id("mt-in-form")
            .asFullWidth().build();

    public static List<MenuToggle> demoToggleSettings = List.of(
            MenuToggle.of("Settings").id("mt-settings").asSettings().icon("fa:gear").build(),
            MenuToggle.of("").id("mt-settings-plain").asPlain().asSettings().icon("fa:gear")
                    .ariaLabel("Settings").build());

    public static List<MenuToggle> demoToggleCustomIcon = List.of(
            MenuToggle.of("CPU usage").id("mt-custom-icon").icon("fa:server").build(),
            MenuToggle.of("Schedule").id("mt-custom-icon-2").icon("fa:clock").build());

    public static List<MenuToggle> demoToggleAvatar = List.of(
            MenuToggle.of("Ned Username").id("mt-avatar").asAvatar().build(),
            MenuToggle.of("Ned Username").id("mt-avatar-expanded").asAvatar().asExpanded().build(),
            MenuToggle.of("Ned Username").id("mt-avatar-disabled").asAvatar().asDisabled().build());

    public static List<MenuToggle> demoToggleBadge = List.of(
            MenuToggle.of("Count").id("mt-badge").badge("4 selected").build(),
            MenuToggle.of("").id("mt-badge-plain").asPlain().asText().badge("4")
                    .ariaLabel("Menu toggle with badge").build());

    public static List<MenuToggle> demoToggleStatus = List.of(
            MenuToggle.of("Success").id("mt-status-success").status("success").build(),
            MenuToggle.of("Warning").id("mt-status-warning").status("warning").build(),
            MenuToggle.of("Danger").id("mt-status-danger").status("danger").build());

    public static MenuToggle demoTogglePlaceholder = MenuToggle.of("Placeholder text").id("mt-placeholder")
            .asPlaceholder().build();

    public static MenuToggle demoToggleTypeahead = MenuToggle.typeahead("mt-typeahead", "Type to filter").build();

    public static List<MenuToggle> demoToggleSplit = List.of(
            MenuToggle.split("mt-split").actionButton("Action").build(),
            MenuToggle.split("mt-split-primary").actionButton("Action").asPrimary().build(),
            MenuToggle.split("mt-split-secondary").actionButton("Action").asSecondary().build());

    public static List<MenuToggle> demoToggleSplitAction = List.of(
            MenuToggle.split("mt-split-action").actionButton("Action").build(),
            MenuToggle.split("mt-split-action-primary").actionButton("Action").asPrimary().build());

    public static List<MenuToggle> demoToggleSplitCheckbox = List.of(
            MenuToggle.split("mt-split-check").checkbox("Select all").build(),
            MenuToggle.split("mt-split-check-primary").checkbox("Select all").asPrimary().build(),
            MenuToggle.split("mt-split-check-secondary").checkbox("Select all").asSecondary().build(),
            MenuToggle.split("mt-split-check-disabled").checkbox("Select all").asDisabled().build());

    public static MenuToggle demoToggleSplitCheckboxText = MenuToggle.split("mt-split-check-text")
            .checkbox("Select all").checkLabel("10 selected").build();

    public static List<MenuToggle> demoToggleSplitCheckboxIconText = List.of(
            MenuToggle.split("mt-split-check-icon-text").checkbox("Select all")
                    .toggleIcon("fa:gear").toggleText("Icon").build(),
            MenuToggle.split("mt-split-check-icon-text-primary").checkbox("Select all")
                    .toggleIcon("fa:gear").toggleText("Icon").asPrimary().build(),
            MenuToggle.split("mt-split-check-icon-text-secondary").checkbox("Select all")
                    .toggleIcon("fa:gear").toggleText("Icon").asSecondary().build());

    public static MenuToggle demoToggleSplitCheckboxToggleText = MenuToggle.split("mt-split-check-toggle-text")
            .checkbox("Select all").splitText("10 selected").build();
}
