import org.sitenetsoft.quarkus.pha.model.*;

TextInput textInput = TextInput.of("ti-invalid")
        .type("email").value("not-an-email").validated("error").build();

// Template side, with the data in scope:
// {#include components/forms/text-input textInput=textInput /}
