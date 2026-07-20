package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Icon;

import java.util.List;

/**
 * Demo data for the icon examples — the examples on /components/icon are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/icon/ served on the example card's Java tab — keep
 * them in sync when editing.
 */
@TemplateGlobal
public class IconDemoData {

    public static Icon demoIconBasic = Icon.of("fa:star").build();

    public static Icon demoIconBodySm = Icon.of("fa:star").size("body-sm").build();

    public static Icon demoIconBodyDefault = Icon.of("fa:star").size("body-default").build();

    public static Icon demoIconBodyLg = Icon.of("fa:star").size("body-lg").build();

    public static Icon demoIconDlProgress = Icon.of("fa:download").size("xl")
            .inProgress().progressIcon("fa:hourglass-half").label("Downloading").build();

    public static Icon demoIconUlProgress = Icon.of("fa:upload").size("xl")
            .inProgress().label("Uploading").build();

    public static Icon demoIconHeadingSm = Icon.of("fa:star").size("heading-sm").build();

    public static Icon demoIconHeadingMd = Icon.of("fa:star").size("heading-md").build();

    public static Icon demoIconHeadingLg = Icon.of("fa:star").size("heading-lg").build();

    public static Icon demoIconHeadingXl = Icon.of("fa:star").size("heading-xl").build();

    public static Icon demoIconHeading2xl = Icon.of("fa:star").size("heading-2xl").build();

    public static Icon demoIconHeading3xl = Icon.of("fa:star").size("heading-3xl").build();

    public static Icon demoIconInlineCheck = Icon.of("fa:circle-check").inline().label("check").build();

    public static Icon demoIconInlineWarn = Icon.of("fa:triangle-exclamation").inline().label("warning").build();

    public static Icon demoIconInProgress = Icon.of("fa:save").size("xl").inProgress().label("Saving").build();

    public static List<Icon> demoIconsSets = List.of(
            Icon.of("fa:circle-check").size("2xl").label("Solid check").build(),
            Icon.of("fa-regular:envelope").size("2xl").label("Regular envelope").build(),
            Icon.of("fa-brands:github").size("2xl").label("GitHub").build(),
            Icon.of("pficon:cluster").size("2xl").label("Cluster").build());

    public static List<Icon> demoIconsSizes = List.of(
            Icon.of("fa:circle-info").size("sm").build(),
            Icon.of("fa:circle-info").size("md").build(),
            Icon.of("fa:circle-info").size("lg").build(),
            Icon.of("fa:circle-info").size("xl").build(),
            Icon.of("fa:circle-info").size("2xl").build(),
            Icon.of("fa:circle-info").size("3xl").build());

    public static List<Icon> demoIconsContainer = List.of(
            Icon.of("fa:circle-info").size("3xl").iconSize("sm").label("3xl container, sm glyph").build(),
            Icon.of("fa:circle-info").size("3xl").iconSize("md").label("3xl container, md glyph").build(),
            Icon.of("fa:circle-info").size("3xl").iconSize("lg").label("3xl container, lg glyph").build(),
            Icon.of("fa:circle-info").size("3xl").iconSize("xl").label("3xl container, xl glyph").build(),
            Icon.of("fa:circle-info").size("3xl").iconSize("3xl").label("3xl container, 3xl glyph").build());

    public static List<Icon> demoIconsStatus = List.of(
            Icon.of("fa:circle-info").size("xl").status("info").label("Info").build(),
            Icon.of("fa:circle-check").size("xl").status("success").label("Success").build(),
            Icon.of("fa:triangle-exclamation").size("xl").status("warning").label("Warning").build(),
            Icon.of("fa:circle-exclamation").size("xl").status("danger").label("Danger").build(),
            Icon.of("fa:bell").size("xl").status("custom").label("Custom").build());

    public static Icon demoIconLabeled = Icon.of("fa:trash").size("lg").label("Delete item").build();

    public static Icon demoIconDecorative = Icon.of("fa:trash").size("lg").build();
}
