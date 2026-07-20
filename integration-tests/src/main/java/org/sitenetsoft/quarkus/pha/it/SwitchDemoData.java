package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Switch;

import java.util.List;

/**
 * Demo data for the switch examples — the examples on /components/switch are
 * populated from these models (including without-label, previously hand-written:
 * the model adds the ariaLabel the param shell lacked). Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/switch/ served
 * on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class SwitchDemoData {

    public static Switch demoSwBasic = Switch.of("sw-basic").label("Enable notifications").build();

    public static Switch demoSwChecked = Switch.of("sw-checked").label("Already on").checked().build();

    public static List<Switch> demoSwsDisabled = List.of(
            Switch.of("sw-dis-off").label("Disabled off").disabled().build(),
            Switch.of("sw-dis-on").label("Disabled on").checked().disabled().build());

    public static Switch demoSwReversed = Switch.of("sw-rev")
            .label("Label first, then the toggle").reversed().build();

    public static Switch demoSwWithoutLabel = Switch.of("sw-without-label")
            .ariaLabel("Enable feature").checked().build();
}
