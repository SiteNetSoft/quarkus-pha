import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-inline-compact", "2.3.4-2-redhat").inline().build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
