import org.sitenetsoft.quarkus.pha.model.*;

TextInput textInput = TextInput.of("ti-readonly")
        .value("Read-only value").readonly().build();

// Template side, with the data in scope:
// {#include components/forms/text-input textInput=textInput /}
