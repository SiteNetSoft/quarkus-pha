package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.TextInput;

import java.util.List;

/**
 * Demo data for the form-control examples — the examples on
 * /components/form-control reuse the TextInput model (form-control is the
 * wrapper anatomy TextInput renders). Globals so the standalone example route
 * (which renders templates without data) can see them. Each is mirrored by a
 * snippet in resources/code-samples/form-control/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class FormControlDemoData {

    public static TextInput demoFcBasic = TextInput.of("fc-basic").placeholder("Enter value").build();

    public static List<TextInput> demoFcsDisabledReadonly = List.of(
            TextInput.of("fc-disabled").value("Disabled value").disabled().build(),
            TextInput.of("fc-readonly").value("Read-only value").readonly().build());

    public static List<TextInput> demoFcsValidated = List.of(
            TextInput.of("fc-success").value("user@example.com").validated("success").build(),
            TextInput.of("fc-warning").value("weak password").validated("warning").build(),
            TextInput.of("fc-error").value("not-an-email").validated("error").build());
}
