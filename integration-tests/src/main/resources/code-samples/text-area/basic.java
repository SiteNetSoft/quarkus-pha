import org.sitenetsoft.quarkus.pha.model.*;

TextArea textArea = TextArea.of("ta-basic")
        .placeholder("Enter your message…").rows(4).build();

// Template side, with the data in scope:
// {#include components/forms/text-area textArea=textArea /}
