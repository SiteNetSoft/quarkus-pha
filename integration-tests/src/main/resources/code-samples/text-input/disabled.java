import org.sitenetsoft.quarkus.pha.model.*;

TextInput textInput = TextInput.of("ti-disabled")
        .value("Disabled value").disabled().build();

// Template side, with the data in scope:
// {#include components/forms/text-input textInput=textInput /}
