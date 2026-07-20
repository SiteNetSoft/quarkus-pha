import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<TextInputGroup> items = List.of(
        TextInputGroup.of("tig-status-success").value("Valid value")
                .status("success").ariaLabel("Success input").build(),
        TextInputGroup.of("tig-status-error").value("Invalid value")
                .status("error").ariaLabel("Error input").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/text-input-group textInputGroup=x /}{/for}
