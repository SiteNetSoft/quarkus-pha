import org.sitenetsoft.quarkus.pha.model.*;

TextArea textArea = TextArea.of("ta-resize-h")
        .resize("horizontal").rows(3).placeholder("Drag the corner to resize horizontally").build();

// Template side, with the data in scope:
// {#include components/forms/text-area textArea=textArea /}
