package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.TextInput;

import java.util.List;

/**
 * Demo data for the text-input examples — the examples on /components/text-input
 * are populated from these models (icon-invalid and start-truncated stay
 * hand-written: custom icon-in-utilities anatomy and inline rtl styling the
 * shell does not model). Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/text-input/ served on the example card's Java tab
 * — keep them in sync when editing.
 */
@TemplateGlobal
public class TextInputDemoData {

    public static TextInput demoTiBasic = TextInput.of("ti-basic").placeholder("Enter value").build();

    public static TextInput demoTiDisabled = TextInput.of("ti-disabled")
            .value("Disabled value").disabled().build();

    public static TextInput demoTiInvalid = TextInput.of("ti-invalid")
            .type("email").value("not-an-email").validated("error").build();

    public static TextInput demoTiReadonly = TextInput.of("ti-readonly")
            .value("Read-only value").readonly().build();

    public static List<TextInput> demoTisTypes = List.of(
            TextInput.of("ti-type-email").type("email").placeholder("email@example.com").build(),
            TextInput.of("ti-type-password").type("password").placeholder("••••••••").build(),
            TextInput.of("ti-type-number").type("number").placeholder("42").build(),
            TextInput.of("ti-type-date").type("date").build());

    public static TextInput demoTiIcon = TextInput.of("ti-icon")
            .type("search").placeholder("Search").icon("fa:magnifying-glass").build();
}
