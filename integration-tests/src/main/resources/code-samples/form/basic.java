import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-basic")
        .group(Group.of("Name", "form-basic-name-field")
                .control(TextInput.of("form-basic-name").placeholder("Enter name").build()).build())
        .group(Group.of("Email", "form-basic-email-field")
                .control(TextInput.of("form-basic-email").type("email")
                        .placeholder("name@example.com").build()).build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
