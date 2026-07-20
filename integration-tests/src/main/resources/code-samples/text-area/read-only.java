import org.sitenetsoft.quarkus.pha.model.*;

TextArea textArea = TextArea.of("ta-read-only-solo")
        .readonly().value("Read-only content stays selectable but not editable.").build();

// Template side, with the data in scope:
// {#include components/forms/text-area textArea=textArea /}
