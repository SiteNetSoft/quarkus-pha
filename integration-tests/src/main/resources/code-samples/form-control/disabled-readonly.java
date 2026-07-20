import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<TextInput> inputs = List.of(
        TextInput.of("fc-disabled").value("Disabled value").disabled().build(),
        TextInput.of("fc-readonly").value("Read-only value").readonly().build());

// Template side, with the data in scope:
// {#for t in inputs}{#include components/forms/text-input textInput=t /}{/for}
