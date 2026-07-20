package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Popover;

import java.util.List;

/**
 * Demo data for the popover examples — the examples on /components/popover are
 * populated from these models; the model renders the full live pattern
 * (trigger, Alpine wrapper, box). Globals so the standalone example route
 * (which renders templates without data) can see them. Each is mirrored by a
 * snippet in resources/code-samples/popover/ served on the example card's Java
 * tab — keep them in sync when editing.
 */
@TemplateGlobal
public class PopoverDemoData {

    private static final String BARE_CHROME = "position: relative; display: inline-block";

    public static Popover demoPoBasic = Popover.of("po-basic").trigger("Show popover")
            .title("Popover header").dismissable()
            .body("Popovers are triggered by click rather than hover. Click the trigger again,"
                    + " the X, or outside to dismiss.")
            .footerText("Popover footer").build();

    public static List<Popover> demoPosPositions = List.of(
            positioned("top", "Top", "bottom: 100%; left: 50%; transform: translateX(-50%)"),
            positioned("right", "Right", "left: 100%; top: 50%; transform: translateY(-50%)"),
            positioned("bottom", "Bottom", "top: 100%; left: 50%; transform: translateX(-50%)"),
            positioned("left", "Left", "right: 100%; top: 50%; transform: translateY(-50%)"),
            positioned("left-top", "Left top", "right: 100%; top: 0"),
            positioned("left-bottom", "Left bottom", "right: 100%; bottom: 0"),
            positioned("bottom-left", "Bottom left", "top: 100%; left: 0"),
            positioned("bottom-right", "Bottom right", "top: 100%; right: 0"));

    private static Popover positioned(String pos, String label, String anchor) {
        return Popover.of("po-pos-" + pos).trigger(label).position(pos)
                .chromeStyle(BARE_CHROME)
                .wrapperStyle("position: absolute; " + anchor + "; width: 300px; z-index: 300")
                .title("Popover header").dismissable()
                .body("The pf-m-" + pos + " modifier places the arrow accordingly.")
                .footerText("Popover footer").build();
    }

    public static Popover demoPoDanger = Popover.of("po-danger").staticOpen()
            .trigger("Delete").triggerVariant("danger").variant("danger")
            .chromeStyle("position: relative; display: inline-block; padding: 4rem 0")
            .wrapperStyle("position: absolute; bottom: 100%; left: 50%; transform: translateX(-50%);"
                    + " width: 320px; visibility: visible")
            .titlePlain("Delete this project?")
            .body("This action cannot be undone. All associated data will be removed.")
            .footerButton("Delete", "pf-m-danger", false)
            .footerButton("Cancel", "pf-m-link", false).build();

    public static Popover demoPoNoHeaderFooter = Popover.of("po-no-header-footer")
            .trigger("Show plain popover").ariaLabel("Plain popover")
            .body("Body only — no header, footer, close button, or padding.").build();

    public static List<Popover> demoPosAlerts = List.of(
            Popover.of("po-alert-custom").trigger("Show custom popover").variant("custom")
                    .title("Custom popover").dismissable()
                    .body("Severity styling via pf-m-custom; the custom variant carries no title icon.").build(),
            Popover.of("po-alert-info").trigger("Show info popover").variant("info")
                    .title("Info popover").titleIcon("fa:circle-info").dismissable()
                    .body("Severity styling via pf-m-info; the title icon picks up the status color.").build(),
            Popover.of("po-alert-success").trigger("Show success popover").variant("success")
                    .title("Success popover").titleIcon("fa:circle-check").dismissable()
                    .body("Severity styling via pf-m-success; the title icon picks up the status color.").build(),
            Popover.of("po-alert-warning").trigger("Show warning popover").variant("warning")
                    .title("Warning popover").titleIcon("fa:exclamation-triangle").dismissable()
                    .body("Severity styling via pf-m-warning; the title icon picks up the status color.").build());

    public static Popover demoPoIconTitle = Popover.of("po-icon-in-title")
            .trigger("Show icon-title popover")
            .title("Title with icon").titleIcon("fa:rocket").dismissable()
            .body("The icon sits in the __title-icon slot before the title text.").build();

    public static Popover demoPoNoPadding = Popover.of("po-no-padding")
            .trigger("Show no-padding popover").chromeStyle(BARE_CHROME)
            .wrapperStyle("position: absolute; left: 100%; top: 50%; transform: translateY(-50%);"
                    + " width: 320px; z-index: 300")
            .position("right").noPadding()
            .ariaLabel("Popover with no padding").describedByBody()
            .body("This popover has no padding itself. It is intended for content that has its own"
                    + " padding and would otherwise sit too far from the popover edges.").build();

    public static Popover demoPoWidthAuto = Popover.of("po-width-auto")
            .trigger("Show auto-width popover").widthAuto()
            .wrapperStyle("position: absolute; bottom: 100%; left: 50%; transform: translateX(-50%);"
                    + " width: auto; z-index: 300")
            .ariaLabel("Width auto").body("Sizes to its content.").build();

    public static Popover demoPoCloseFromContent = Popover.of("po-close-from-content")
            .trigger("Show popover")
            .title("Close from content").dismissable()
            .body("An action inside the popover body or footer can dismiss it — PF documents this"
                    + " as the controlled and uncontrolled close patterns.")
            .footerButton("Got it", "pf-m-primary", true).build();

    public static Popover demoPoHoverable = Popover.of("po-hoverable").hoverable()
            .trigger("Hover or focus me")
            .title("Hoverable popover")
            .body("This popover opens on hover or keyboard focus and closes when the pointer leaves.").build();

    public static Popover demoPoAdvanced = Popover.of("po-advanced")
            .trigger("Show advanced popover")
            .title("Advanced popover").titleIcon("fa:circle-info").dismissable()
            .body("Header with an icon, a dismissable close button, and footer actions together.")
            .footerButton("Dismiss", "pf-m-link pf-m-inline", true).build();
}
