import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-invalid")
        .group(Group.of("Email", "form-invalid-email-field")
                .control(TextInput.of("form-invalid-email").type("email")
                        .value("not-an-email").validated("error").build())
                .helper("Enter a valid email address.", "error").build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
