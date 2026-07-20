import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-sections").style("max-width: 480px")
        .section("Section 1",
                Group.of("Form section 1 input", "form-sections-f1")
                        .controlHtml("<span class=\"pf-v6-c-form-control\">"
                                + "<input type=\"text\" id=\"form-sections-f1\" /></span>").build())
        .section("Section 2 (optional)",
                Group.of("Form section 2 input", "form-sections-f2")
                        .controlHtml("<span class=\"pf-v6-c-form-control\">"
                                + "<input type=\"text\" id=\"form-sections-f2\" /></span>").build())
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
