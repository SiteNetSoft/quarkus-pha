package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.HelperText;
import org.sitenetsoft.quarkus.pha.model.HelperText.Item;

import java.util.List;

/**
 * Demo data for the helper-text examples — the examples on
 * /components/helper-text are populated from these models. Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/helper-text/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class HelperTextDemoData {

    public static List<HelperText> demoHtsBasic = List.of(
            HelperText.of("ht-basic-static", "This is default helper text").build(),
            HelperText.of("ht-basic-indeterminate", "This is indeterminate helper text")
                    .variant("indeterminate").build(),
            HelperText.of("ht-basic-warning", "This is warning helper text").variant("warning").build(),
            HelperText.of("ht-basic-success", "This is success helper text").variant("success").build(),
            HelperText.of("ht-basic-error", "This is error helper text").variant("error").build());

    public static HelperText demoHtMulti = HelperText.list("ht-multi-pwd")
            .item(Item.of("At least 8 characters").variant("success"))
            .item(Item.of("Contains an uppercase letter").variant("success"))
            .item(Item.of("Contains a number").variant("warning"))
            .item(Item.of("Contains a special character").variant("error"))
            .build();

    public static List<HelperText> demoHtsIcons = List.of(
            HelperText.of("ht-icon-info", "Pro tip: use a strong password.")
                    .variant("success").icon("fa:lightbulb").build(),
            HelperText.of("ht-icon-warning", "Your session will expire soon.")
                    .variant("warning").icon("fa:clock").build(),
            HelperText.of("ht-icon-error", "Account temporarily locked.")
                    .variant("error").icon("fa:lock").build());
}
