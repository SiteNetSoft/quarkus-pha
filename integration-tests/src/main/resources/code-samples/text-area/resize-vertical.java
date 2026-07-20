import org.sitenetsoft.quarkus.pha.model.*;

TextArea textArea = TextArea.of("ta-resize-v")
        .resize("vertical").rows(3).placeholder("Drag the corner to resize vertically").build();

// Template side, with the data in scope:
// {#include components/forms/text-area textArea=textArea /}
