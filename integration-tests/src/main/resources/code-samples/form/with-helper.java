import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-helper")
        .group(Group.of("Display name", "form-helper-name-field").required()
                .control(TextInput.of("form-helper-name").required()
                        .placeholder("Enter your display name").build())
                .helper("This is the name other users will see.").build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
