import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-inline", "npm install").inline().code().build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
