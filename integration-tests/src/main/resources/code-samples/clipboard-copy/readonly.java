import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-readonly", "ghp_a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7").readonly().build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
