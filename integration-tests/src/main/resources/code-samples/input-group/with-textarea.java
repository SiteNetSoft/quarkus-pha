import org.sitenetsoft.quarkus.pha.model.*;

InputGroup inputGroup = InputGroup.of("ig-textarea")
        .fill("<span class=\"pf-v6-c-form-control pf-m-textarea\"><textarea id=\"ig-textarea-field\""
                + " rows=\"3\" aria-label=\"Message\"></textarea></span>")
        .button(Button.of("Send").variant("control").build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/input-group inputGroup=inputGroup /}
