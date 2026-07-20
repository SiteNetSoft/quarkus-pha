import org.sitenetsoft.quarkus.pha.model.*;

TextInput textInput = TextInput.of("fc-basic").placeholder("Enter value").build();

// Template side, with the data in scope:
// {#include components/forms/text-input textInput=textInput /}
