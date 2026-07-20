import org.sitenetsoft.quarkus.pha.model.*;

TextInput textInput = TextInput.of("ti-icon")
        .type("search").placeholder("Search").icon("fa:magnifying-glass").build();

// Template side, with the data in scope:
// {#include components/forms/text-input textInput=textInput /}
