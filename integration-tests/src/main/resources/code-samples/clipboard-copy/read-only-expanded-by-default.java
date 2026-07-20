import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-ro-expanded-default", "Starts expanded").readonly().expandable().expanded()
        .expandedText("This panel is open on load thanks to the builder's expanded() call.").build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
