import org.sitenetsoft.quarkus.pha.model.*;

TextArea textArea = TextArea.of("ta-invalid")
        .rows(3).value("Too short.").validated("error").build();

// Template side, with the data in scope:
// {#include components/forms/text-area textArea=textArea /}
