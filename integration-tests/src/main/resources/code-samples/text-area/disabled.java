import org.sitenetsoft.quarkus.pha.model.*;

TextArea textArea = TextArea.of("ta-disabled-solo")
        .disabled().value("Disabled text area").build();

// Template side, with the data in scope:
// {#include components/forms/text-area textArea=textArea /}
