import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-json-pre", "{ \"kind\": \"ConfigMap\", ... }").expanded()
        .expandedHtml("<pre>{ \"kind\": \"ConfigMap\", \"metadata\": { \"name\": \"app-config\" },"
                + " \"data\": { \"LOG_LEVEL\": \"info\" } }</pre>").build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
