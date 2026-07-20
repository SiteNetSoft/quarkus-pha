import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-limit-width").limitWidth()
        .group(Group.of("Name", "form-limit-width-name-field")
                .controlHtml("<span class=\"pf-v6-c-form-control\">"
                        + "<input type=\"text\" id=\"form-limit-width-name-field\" /></span>").build())
        .group(Group.of("Email", "form-limit-width-email-field")
                .controlHtml("<span class=\"pf-v6-c-form-control\">"
                        + "<input type=\"text\" id=\"form-limit-width-email-field\" /></span>").build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
