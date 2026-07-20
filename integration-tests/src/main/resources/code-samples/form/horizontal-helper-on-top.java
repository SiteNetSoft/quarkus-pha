import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-horizontal-helper-on-top").horizontal()
        .group(Group.of("Label", "form-hhot-field").noPaddingTop()
                .controlHtml("<span class=\"pf-v6-c-form-control\">"
                        + "<input type=\"text\" id=\"form-hhot-field\" /></span>")
                .helper("Helper text shown above the control.").helperOnTop().build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
