import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-inline-truncate", "https://app.example.com/api/v2/deployments/3f9c2a17-55de-4d1b-9d6f")
        .inline().block()
        .textStyle("overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                + " display: inline-block; max-width: 170px; vertical-align: bottom").build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
