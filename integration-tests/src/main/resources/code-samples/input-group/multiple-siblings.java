import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<InputGroup> items = List.of(
        InputGroup.of("ig-sib-1")
                .boxText("$")
                .fill("<span class=\"pf-v6-c-form-control\"><input type=\"number\""
                        + " aria-label=\"Amount in dollars\" value=\"100\" /></span>")
                .boxText(".00")
                .build(),
        InputGroup.of("ig-sib-2")
                .fill("<span class=\"pf-v6-c-form-control\"><input type=\"text\""
                        + " aria-label=\"Hostname\" placeholder=\"Hostname\" /></span>")
                .boxTextDisabled(".internal")
                .build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/input-group inputGroup=x /}{/for}
