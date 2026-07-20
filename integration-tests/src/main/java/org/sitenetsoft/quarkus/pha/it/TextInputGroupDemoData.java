package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.TextInputGroup;

import java.util.List;

/**
 * Demo data for the text-input-group examples — the examples on
 * /components/text-input-group are populated from these models (with-icon and
 * the two filters examples stay hand-written: live Alpine clear/chip
 * utilities). Globals so the standalone example route (which renders templates
 * without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/text-input-group/ served on the example card's Java
 * tab — keep them in sync when editing.
 */
@TemplateGlobal
public class TextInputGroupDemoData {

    public static TextInputGroup demoTigBasic = TextInputGroup.of("tig-basic")
            .placeholder("Type here…").build();

    public static TextInputGroup demoTigDisabled = TextInputGroup.of("tig-disabled")
            .placeholder("Disabled").disabled().build();

    public static TextInputGroup demoTigPlain = TextInputGroup.of("tig-plain").plain()
            .value("Text input group with plain styling").ariaLabel("Plain text input group").build();

    public static TextInputGroup demoTigHint = TextInputGroup.of("tig-hint")
            .value("apples").hint("appleseed").ariaLabel("Autocomplete with last option hint").build();

    public static List<TextInputGroup> demoTigsStatus = List.of(
            TextInputGroup.of("tig-status-success").value("Valid value")
                    .status("success").ariaLabel("Success input").build(),
            TextInputGroup.of("tig-status-error").value("Invalid value")
                    .status("error").ariaLabel("Error input").build());
}
