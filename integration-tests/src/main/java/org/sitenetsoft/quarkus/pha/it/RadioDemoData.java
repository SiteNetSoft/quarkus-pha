package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Radio;

import java.util.List;

/**
 * Demo data for the radio examples — the examples on /components/radio are
 * populated from these models (controlled stays hand-written: live Alpine
 * selection state; reversed stays hand-written: the shell has no label-first
 * anatomy for radios). Globals so the standalone example route (which renders
 * templates without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/radio/ served on the example card's Java tab — keep
 * them in sync when editing.
 */
@TemplateGlobal
public class RadioDemoData {

    public static List<Radio> demoRadiosBasic = List.of(
            Radio.of("r-basic-one", "r-basic").label("Option one").checked().build(),
            Radio.of("r-basic-two", "r-basic").label("Option two").build());

    public static Radio demoRadioDescBody = Radio.of("rd-desc-body", "rd-desc-body")
            .label("With description and body")
            .description("A longer explanation of what selecting this option means.")
            .body("Extra body content - links or fine print can live here.").build();

    public static List<Radio> demoRadiosDisabled = List.of(
            Radio.of("r-dis-one", "r-dis").label("Disabled, unselected").disabled().build(),
            Radio.of("r-dis-two", "r-dis").label("Disabled, selected").checked().disabled().build());

    public static Radio demoRadioLabelWraps = Radio.of("rd-label-wraps", "rd-label-wraps")
            .label("A really long radio label that wraps onto several lines while the input"
                    + " stays aligned to the first line").build();

    public static Radio demoRadioStandalone = Radio.standalone("r-standalone", "r-standalone").build();

    public static Radio demoRadioBody = Radio.of("r-body", "r-body").label("Custom plan")
            .body("Choose features individually after picking this option.").checked().build();

    public static List<Radio> demoRadiosDesc = List.of(
            Radio.of("r-desc-one", "r-desc").label("Standard")
                    .description("3 GB storage, basic support.").checked().build(),
            Radio.of("r-desc-two", "r-desc").label("Pro")
                    .description("30 GB storage, priority support.").build(),
            Radio.of("r-desc-three", "r-desc").label("Enterprise")
                    .description("Unlimited storage, dedicated account manager.").build());
}
