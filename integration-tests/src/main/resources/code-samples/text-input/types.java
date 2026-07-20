import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<TextInput> items = List.of(
        TextInput.of("ti-type-email").type("email").placeholder("email@example.com").build(),
        TextInput.of("ti-type-password").type("password").placeholder("••••••••").build(),
        TextInput.of("ti-type-number").type("number").placeholder("42").build(),
        TextInput.of("ti-type-date").type("date").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/text-input textInput=x /}{/for}
