import org.sitenetsoft.quarkus.pha.model.*;

InputGroup inputGroup = InputGroup.of("ig-basic")
        .boxText("@")
        .fill("<span class=\"pf-v6-c-form-control\"><input type=\"email\" id=\"ig-basic-email\""
                + " aria-label=\"Email address\" placeholder=\"Email address\" /></span>")
        .boxText(".example.com")
        .build();

// Template side, with the data in scope:
// {#include components/forms/input-group inputGroup=inputGroup /}
