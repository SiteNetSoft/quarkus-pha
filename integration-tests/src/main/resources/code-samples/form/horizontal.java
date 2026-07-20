import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-horizontal").horizontal()
        .group(Group.of("Name", "form-h-name-field")
                .control(TextInput.of("form-h-name").placeholder("Enter name").build()).build())
        .group(Group.of("Email", "form-h-email-field")
                .control(TextInput.of("form-h-email").type("email")
                        .placeholder("name@example.com").build()).build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
