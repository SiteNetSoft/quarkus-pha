import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-grid")
        .grid(GridItem.of("pf-m-6-col-on-md",
                        Group.of("First name", "form-grid-first")
                                .controlHtml("<span class=\"pf-v6-c-form-control\">"
                                        + "<input type=\"text\" id=\"form-grid-first\" /></span>").build()),
                GridItem.of("pf-m-6-col-on-md",
                        Group.of("Last name", "form-grid-last")
                                .controlHtml("<span class=\"pf-v6-c-form-control\">"
                                        + "<input type=\"text\" id=\"form-grid-last\" /></span>").build()),
                GridItem.of("pf-m-12-col",
                        Group.of("Email", "form-grid-email")
                                .controlHtml("<span class=\"pf-v6-c-form-control\">"
                                        + "<input type=\"email\" id=\"form-grid-email\" /></span>").build()))
        .build();

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
