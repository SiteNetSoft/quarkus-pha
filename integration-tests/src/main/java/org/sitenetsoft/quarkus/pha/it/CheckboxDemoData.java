package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Check;

import java.util.List;

/**
 * Demo data for the checkbox examples — the examples on /components/checkbox
 * are populated from these models (controlled stays hand-written: live Alpine
 * selection state; label-wraps and description-and-body stay hand-written:
 * they use the div+label[for] anatomy, not the shell's label-wrapper anatomy).
 * Globals so the standalone example route (which renders templates without
 * data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/checkbox/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class CheckboxDemoData {

    public static Check demoCbBasic = Check.of("cb-basic").label("Accept terms").build();

    public static Check demoCbChecked = Check.of("cb-checked").label("Already checked").checked().build();

    public static List<Check> demoCbsDisabled = List.of(
            Check.of("cb-disabled").label("Disabled, unchecked").disabled().build(),
            Check.of("cb-disabled-checked").label("Disabled, checked").checked().disabled().build());

    public static Check demoCbRequired = Check.of("cb-required").label("I agree to the terms").required().build();

    public static Check demoCbReversed = Check.of("cb-reversed")
            .label("Label first, then the input").reversed().build();

    public static Check demoCbStandalone = Check.standalone("cb-standalone", "Select all rows").build();

    public static Check demoCbBody = Check.of("cb-body").label("Customize delivery")
            .body("Choose hours and days for digest delivery (configurable after enabling).").build();

    public static Check demoCbDesc = Check.of("cb-desc").label("Email me weekly digests")
            .description("Includes activity summary and recommended reads. You can unsubscribe at any time.")
            .build();
}
