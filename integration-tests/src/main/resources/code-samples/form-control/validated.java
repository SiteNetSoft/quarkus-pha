import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<TextInput> inputs = List.of(
        TextInput.of("fc-success").value("user@example.com").validated("success").build(),
        TextInput.of("fc-warning").value("weak password").validated("warning").build(),
        TextInput.of("fc-error").value("not-an-email").validated("error").build());

// Template side, with the data in scope:
// {#for t in inputs}{#include components/forms/text-input textInput=t /}{/for}
