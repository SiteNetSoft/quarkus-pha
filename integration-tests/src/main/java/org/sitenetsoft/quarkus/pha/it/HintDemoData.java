package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Hint;

/**
 * Demo data for the hint examples — the examples on /components/hint are
 * populated from these models (with-content previously hand-rendered around
 * the slot-name collision: the model's raw-HTML footer replaces the slot
 * entirely). Globals so the standalone example route (which renders templates
 * without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/hint/ served on the example card's Java tab — keep
 * them in sync when editing.
 */
@TemplateGlobal
public class HintDemoData {

    public static Hint demoHintBasic = Hint.of("ht-basic").title("Pro tip")
            .body("You can drag and drop files onto the upload zone — they'll auto-upload"
                    + " in batches of five.").build();

    public static Hint demoHintWithContent = Hint.of("ht-content").title("Hint with action")
            .body("Need to migrate? Read the migration guide before upgrading.")
            .footerHtml("<a class=\"pf-v6-c-button pf-m-link pf-m-inline\" href=\"#\">"
                    + "<span class=\"pf-v6-c-button__text\">Read the guide</span></a>")
            .build();

    public static Hint demoHintWithoutTitle = Hint.of("ht-without-title")
            .body("A hint without a title — just the body text drawing attention to something useful.")
            .build();
}
