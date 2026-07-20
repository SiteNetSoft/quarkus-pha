package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Modal;

/**
 * Demo data for the modal examples — the examples on /components/modal are
 * populated from these models (sizes, custom-header, custom-focus, with-help,
 * with-form and with-dropdown stay hand-written: live inner-state / custom
 * anatomy compositions). Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/modal/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class ModalDemoData {

    private static final String ALERT_BODY = "<p>\n"
            + "    The status modifier colors the title bar icon and adds a screen-reader-only"
            + " alert prefix to the title.\n  </p>";

    private static Modal alert(String id, String variant, String title) {
        return Modal.of(id).size("md").variant(variant).alertPrefix()
                .trigger("Open " + title).title(title).headingLevel("h2")
                .body(ALERT_BODY)
                .footerButton("Confirm", "pf-m-primary")
                .footerButton("Cancel", "pf-m-link").build();
    }

    public static Modal demoMoBasic = Modal.of("mo-basic").size("md").trigger("Open modal")
            .title("Are you sure?").headingLevel("h2")
            .body("<p>This action removes the project and all its associated data. It cannot be undone.</p>")
            .footerButton("Delete", "pf-m-danger")
            .footerButton("Cancel", "pf-m-link").build();

    public static Modal demoMoInfoAlert = alert("mo-info-alert", "info", "info alert modal");
    public static Modal demoMoSuccessAlert = alert("mo-success-alert", "success", "success alert modal");
    public static Modal demoMoWarningAlert = alert("mo-warning-alert", "warning", "warning alert modal");
    public static Modal demoMoDangerAlert = alert("mo-danger-alert", "danger", "danger alert modal");
    public static Modal demoMoCustomAlert = alert("mo-custom-alert", "custom", "custom alert modal");

    public static Modal demoMoWithDescription = Modal.of("mo-with-description").size("md")
            .trigger("Open modal with description")
            .title("With a static description").headingLevel("h2")
            .description("A static description appears here, below the modal title.")
            .body("<p>The description sits in the header below the title and does not scroll with the body.</p>")
            .footerButton("Confirm", "pf-m-primary")
            .footerButton("Cancel", "pf-m-link").build();

    public static Modal demoMoWithoutTitle = Modal.of("mo-without-title").size("md")
            .trigger("Open modal without a title")
            .ariaLabel("Example of a modal without a title")
            .body("<p>\n    When a modal has no title element, the modal box itself must carry an\n"
                    + "    <code class=\"ws-code\">aria-label</code> naming the dialog for assistive"
                    + " technologies.\n  </p>")
            .footerButton("Close", "pf-m-primary").build();

    public static Modal demoMoNoHeaderFooter = Modal.of("mo-no-header-footer").size("md")
            .trigger("Open header/footer-less modal")
            .ariaLabel("Modal without header or footer")
            .body("<p>Only a body — no header, no footer. The close button still floats in the corner.</p>")
            .build();

    public static Modal demoMoTopAligned = Modal.of("mo-top-aligned").size("md").alignTop()
            .trigger("Open top-aligned modal")
            .title("Top aligned").headingLevel("h2")
            .body("<p><code class=\"ws-code\">pf-m-align-top</code> pins the dialog to the top of the viewport.</p>")
            .footerButton("Confirm", "pf-m-primary")
            .footerButton("Cancel", "pf-m-link").build();

    public static Modal demoMoCustomWidth = Modal.of("mo-custom-width").width("50%")
            .trigger("Open 50%-width modal")
            .title("Custom width").headingLevel("h2")
            .body("<p>An inline width (here 50%) replaces the size modifier.</p>")
            .footerButton("Confirm", "pf-m-primary").build();

    public static Modal demoMoTitleIcon = Modal.of("mo-title-icon").size("md").variant("warning")
            .trigger("Open warning modal")
            .title("Warning modal").headingLevel("h2")
            .body("<p>The <code class=\"ws-code\">pf-m-warning</code> variant colors the title icon.</p>")
            .footerButton("Confirm", "pf-m-primary")
            .footerButton("Cancel", "pf-m-link").build();

    public static Modal demoMoCustomTitleIcon = Modal.of("mo-custom-title-icon").size("md")
            .trigger("Open custom-icon modal")
            .title("Custom icon modal").headingLevel("h2").titleIcon("fa:rocket")
            .body("<p>Any icon can sit in the title icon slot.</p>")
            .footerButton("Confirm", "pf-m-primary")
            .footerButton("Cancel", "pf-m-link").build();

    public static Modal demoMoDangerStatusTitle = Modal.of("mo-danger-status-title").size("md")
            .variant("danger").statusTitle().alertPrefix()
            .trigger("Open danger alert modal (title modifier)")
            .title("danger alert modal").headingLevel("h2")
            .body("<p>\n    Here <code class=\"ws-code\">pf-m-danger</code> sits on the title element"
                    + " instead of the modal box — the box carries\n    no status class.\n  </p>")
            .footerButton("Confirm", "pf-m-primary")
            .footerButton("Cancel", "pf-m-link").build();

    public static Modal demoMoScrollable = Modal.of("mo-scrollable").size("md")
            .trigger("Open scrollable modal")
            .title("Scrollable modal").headingLevel("h2").bodyScrollable()
            .body(scrollBody())
            .footerButton("Confirm", "pf-m-primary").build();

    private static String scrollBody() {
        String p = "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus pretium"
                + " est a porttitor vehicula. Quisque vel commodo urna.</p>";
        return (p + "\n  ").repeat(13) + p;
    }

    public static Modal demoMoGenericContainer = Modal.of("mo-generic-container").size("md")
            .trigger("Open generic modal container")
            .ariaLabel("Generic modal container").noClose()
            .genericHtml("<p style=\"padding: 1rem\">\n    The modal box children elements can be"
                    + " removed, and the modal serves as a generic container. One use case of this\n"
                    + "    is when creating a wizard in a modal. Press <kbd>Escape</kbd> to close"
                    + " this one.\n  </p>")
            .build();
}
