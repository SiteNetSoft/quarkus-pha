import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-group-label-info").style("max-width: 480px")
        .group(Group.of("Full name", "form-gli-field").labelInfo("Additional label info")
                .controlHtml("<span class=\"pf-v6-c-form-control\">"
                        + "<input type=\"text\" id=\"form-gli-field\" /></span>").build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
