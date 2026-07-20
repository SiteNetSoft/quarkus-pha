import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-ro-expanded", "Read-only and expandable").readonly().expandable()
        .expandedText("The expandable panel content — still read-only.").build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
