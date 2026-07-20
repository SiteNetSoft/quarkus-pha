package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.InputGroup;

import java.util.List;

/**
 * Demo data for the input-group examples — the examples on
 * /components/input-group are populated from these models (with-dropdown and
 * with-popover stay hand-written: live Alpine flyout compositions). Globals so
 * the standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/input-group/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class InputGroupDemoData {

    public static InputGroup demoIgBasic = InputGroup.of("ig-basic")
            .boxText("@")
            .fill("<span class=\"pf-v6-c-form-control\"><input type=\"email\" id=\"ig-basic-email\""
                    + " aria-label=\"Email address\" placeholder=\"Email address\" /></span>")
            .boxText(".example.com")
            .build();

    public static List<InputGroup> demoIgsSiblings = List.of(
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

    public static InputGroup demoIgTextarea = InputGroup.of("ig-textarea")
            .fill("<span class=\"pf-v6-c-form-control pf-m-textarea\"><textarea id=\"ig-textarea-field\""
                    + " rows=\"3\" aria-label=\"Message\"></textarea></span>")
            .button(Button.of("Send").variant("control").build())
            .build();
}
