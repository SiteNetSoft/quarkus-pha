package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Button;

import java.util.List;

/**
 * Demo data for the button examples — every example on /components/button
 * except progress-login (an HTMX/Alpine login-form composition with its
 * _login-action partial) is populated from these Button models. Globals so
 * the standalone example route (which renders templates without data) can
 * see them. Each is mirrored by a snippet in resources/code-samples/button/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ButtonDemoData {

    public static List<Button> demoButtonVariants = List.of(
            Button.of("Primary").id("btn-primary").variant("primary").build(),
            Button.of("Secondary").id("btn-secondary").variant("secondary").build(),
            Button.of("Tertiary").id("btn-tertiary").variant("tertiary").build(),
            Button.of("Danger").id("btn-danger").variant("danger").build(),
            Button.of("Warning").id("btn-warning").variant("warning").build(),
            Button.of("Link").id("btn-link").variant("link").build(),
            Button.icon("fa:xmark", "Close").id("btn-plain").variant("plain").build(),
            Button.of("Control").id("btn-control").variant("control").build());

    public static List<Button> demoButtonsDisabled = List.of(
            Button.of("Primary disabled").id("btn-disabled-primary").variant("primary").asDisabled().build(),
            Button.of("Secondary disabled").id("btn-disabled-secondary").variant("secondary").asDisabled().build(),
            Button.of("Tertiary disabled").id("btn-disabled-tertiary").variant("tertiary").asDisabled().build(),
            Button.of("Danger disabled").id("btn-disabled-danger").variant("danger").asDisabled().build(),
            Button.of("Link disabled").id("btn-disabled-link").variant("link").asDisabled().build());

    public static List<Button> demoButtonsAriaDisabled = List.of(
            Button.of("Primary aria-disabled").id("btn-aria-disabled-primary").variant("primary")
                    .asAriaDisabled().build(),
            Button.of("Secondary aria-disabled").id("btn-aria-disabled-secondary").variant("secondary")
                    .asAriaDisabled().build(),
            Button.of("Link aria-disabled").id("btn-aria-disabled-link").variant("link")
                    .asAriaDisabled().build());

    public static List<Button> demoButtonsSmall = List.of(
            Button.of("Small primary").id("btn-sm-primary").variant("primary").size("sm").build(),
            Button.of("Small secondary").id("btn-sm-secondary").variant("secondary").size("sm").build(),
            Button.of("Small tertiary").id("btn-sm-tertiary").variant("tertiary").size("sm").build(),
            Button.of("Small link").id("btn-sm-link").variant("link").size("sm").build());

    public static List<Button> demoButtonsCallToAction = List.of(
            Button.of("Call to action").id("btn-cta-primary").variant("primary").size("lg").build(),
            Button.of("Call to action").id("btn-cta-secondary").variant("secondary").size("lg").build(),
            Button.of("Call to action").id("btn-cta-tertiary").variant("tertiary").size("lg").build(),
            Button.of("Call to action").id("btn-cta-link").variant("link").size("lg").build());

    public static Button demoButtonBlock = Button.of("Block level button spans its container")
            .id("btn-block").variant("primary").asBlock().build();

    public static List<Button> demoButtonsLinks = List.of(
            Button.of("Primary link").id("btn-link-primary").variant("primary")
                    .href("#links-as-buttons").build(),
            Button.of("Secondary link").id("btn-link-secondary").variant("secondary")
                    .href("#links-as-buttons").build(),
            Button.of("Open external").id("btn-link-link").variant("link").href("#links-as-buttons")
                    .icon("fa:arrow-up-right-from-square").iconAtEnd().ariaLabel("Open external").build());

    public static Button demoButtonInlineSpan = Button.of("click here to learn more")
            .id("btn-inline-span").variant("link").asInline().asSpan().build();

    public static List<Button> demoButtonTypes = List.of(
            Button.of("Submit").id("btn-type-submit").type("submit").build(),
            Button.of("Reset").id("btn-type-reset").type("reset").build(),
            Button.of("Default").id("btn-type-default").build());

    public static List<Button> demoButtonsWithCount = List.of(
            Button.of("Notifications").id("btn-count-unread").variant("secondary").count(5).build(),
            Button.of("Messages").id("btn-count-read").variant("secondary").count(12).countRead().build());

    public static List<Button> demoButtonsCircle = List.of(
            Button.icon("fa:circle-plus", "Add (primary circle)").id("btn-circle-primary")
                    .variant("primary").asCircle().build(),
            Button.icon("fa:circle-plus", "Add (secondary circle)").id("btn-circle-secondary")
                    .variant("secondary").asCircle().build(),
            Button.icon("fa:circle-plus", "Add (tertiary circle)").id("btn-circle-tertiary")
                    .variant("tertiary").asCircle().build(),
            Button.icon("fa:circle-plus", "Add (danger circle)").id("btn-circle-danger")
                    .variant("danger").asCircle().build(),
            Button.icon("fa:circle-plus", "Add (warning circle)").id("btn-circle-warning")
                    .variant("warning").asCircle().build(),
            Button.icon("fa:circle-plus", "Add (link circle)").id("btn-circle-link")
                    .variant("link").asCircle().build(),
            Button.icon("fa:copy", "Copy (control circle)").id("btn-circle-control")
                    .variant("control").asCircle().build(),
            Button.icon("fa:xmark", "Remove (plain circle)").id("btn-circle-plain")
                    .variant("plain").asCircle().build(),
            Button.icon("fa:bell", "Notifications, unread (stateful circle)").id("btn-circle-stateful-unread")
                    .variant("stateful").state("unread").asCircle().build(),
            Button.icon("fa:bell", "Notifications, read (stateful circle)").id("btn-circle-stateful-read")
                    .variant("stateful").state("read").asCircle().build(),
            Button.icon("fa:bell", "Notifications, attention (stateful circle)").id("btn-circle-stateful-attention")
                    .variant("stateful").state("attention").asCircle().build());

    public static List<Button> demoButtonsPlainPadding = List.of(
            Button.icon("fa:pen-to-square", "Edit (padded)").id("btn-plain-padded").variant("plain").build(),
            Button.icon("fa:pen-to-square", "Edit (no padding)").id("btn-plain-no-pad")
                    .variant("plain").asNoPadding().build());

    public static List<Button> demoButtonsLoading = List.of(
            Button.of("Saving…").id("btn-loading-primary").variant("primary").loading("Saving").build(),
            Button.of("Loading…").id("btn-loading-secondary").variant("secondary").loading("Loading").build(),
            Button.of("Fetching").id("btn-loading-link").variant("link").loading("Fetching").build());

    public static List<Button> demoButtonsStateful = List.of(
            Button.icon("fa:bell", "Notifications, all read").id("btn-stateful-read")
                    .variant("stateful").state("read").build(),
            Button.icon("fa:bell", "Notifications, unread").id("btn-stateful-unread")
                    .variant("stateful").state("unread").build(),
            Button.icon("fa:bell", "Notifications, needs attention").id("btn-stateful-attention")
                    .variant("stateful").state("attention").build());

    public static List<Button> demoButtonsFavorite = List.of(
            Button.favorite("Add to favorites").id("btn-favorite-off").build(),
            Button.favorite("Remove from favorites").id("btn-favorite-on").asFavorited().build());

    public static List<Button> demoButtonsHamburger = List.of(
            Button.hamburger("Open menu").id("btn-hamburger-default").ariaExpanded("false").build(),
            Button.hamburger("Open menu").id("btn-hamburger-expand").hamburgerVariant("expand")
                    .ariaExpanded("false").build(),
            Button.hamburger("Close menu").id("btn-hamburger-collapse").hamburgerVariant("collapse")
                    .ariaExpanded("true").build());

    public static Button demoButtonSettings = Button.settings("Settings").id("btn-settings").build();

    public static List<Button> demoButtonsCustomComponent = List.of(
            Button.of("Span as a button").id("btn-custom-span").variant("secondary").asSpan().build(),
            Button.of("Div as a button").id("btn-custom-div").variant("tertiary").asDiv().build());
}
