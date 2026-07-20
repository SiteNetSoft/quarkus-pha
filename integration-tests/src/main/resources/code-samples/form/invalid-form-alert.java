import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-invalid-alert").style("max-width: 480px")
        .dangerAlert("Fill out all required fields before continuing.")
        .group(Group.of("Age", "form-invalid-alert-age").required()
                .controlHtml("<span class=\"pf-v6-c-form-control pf-m-required pf-m-error\">"
                        + "<input type=\"text\" id=\"form-invalid-alert-age\" aria-invalid=\"true\""
                        + " value=\"Thirty\" /></span>")
                .helper("Age must be a number.", "error").build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
