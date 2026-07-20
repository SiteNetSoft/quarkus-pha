package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Form;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;
import org.sitenetsoft.quarkus.pha.model.TextInput;

/**
 * Demo data for the form examples — the examples on /components/form are
 * populated from these models (field-groups and validated stay hand-written:
 * expandable field groups and as-you-type validation are live Alpine
 * compositions). Globals so the standalone example route (which renders
 * templates without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/form/ served on the example card's Java tab — keep
 * them in sync when editing.
 */
@TemplateGlobal
public class FormDemoData {

    public static Form demoFormBasic = Form.of("form-basic")
            .group(Group.of("Name", "form-basic-name-field")
                    .control(TextInput.of("form-basic-name").placeholder("Enter name").build()).build())
            .group(Group.of("Email", "form-basic-email-field")
                    .control(TextInput.of("form-basic-email").type("email")
                            .placeholder("name@example.com").build()).build())
            .build();

    public static Form demoFormHorizontal = Form.of("form-horizontal").horizontal()
            .group(Group.of("Name", "form-h-name-field")
                    .control(TextInput.of("form-h-name").placeholder("Enter name").build()).build())
            .group(Group.of("Email", "form-h-email-field")
                    .control(TextInput.of("form-h-email").type("email")
                            .placeholder("name@example.com").build()).build())
            .build();

    public static Form demoFormLimitWidth = Form.of("form-limit-width").limitWidth()
            .group(Group.of("Name", "form-limit-width-name-field")
                    .controlHtml("<span class=\"pf-v6-c-form-control\">"
                            + "<input type=\"text\" id=\"form-limit-width-name-field\" /></span>").build())
            .group(Group.of("Email", "form-limit-width-email-field")
                    .controlHtml("<span class=\"pf-v6-c-form-control\">"
                            + "<input type=\"text\" id=\"form-limit-width-email-field\" /></span>").build())
            .build();

    public static Form demoFormInvalid = Form.of("form-invalid")
            .group(Group.of("Email", "form-invalid-email-field")
                    .control(TextInput.of("form-invalid-email").type("email")
                            .value("not-an-email").validated("error").build())
                    .helper("Enter a valid email address.", "error").build())
            .build();

    public static Form demoFormHelper = Form.of("form-helper")
            .group(Group.of("Display name", "form-helper-name-field").required()
                    .control(TextInput.of("form-helper-name").required()
                            .placeholder("Enter your display name").build())
                    .helper("This is the name other users will see.").build())
            .build();

    public static Form demoFormGrid = Form.of("form-grid")
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

    public static Form demoFormLabelInfo = Form.of("form-group-label-info").style("max-width: 480px")
            .group(Group.of("Full name", "form-gli-field").labelInfo("Additional label info")
                    .controlHtml("<span class=\"pf-v6-c-form-control\">"
                            + "<input type=\"text\" id=\"form-gli-field\" /></span>").build())
            .build();

    public static Form demoFormHelperOnTop = Form.of("form-horizontal-helper-on-top").horizontal()
            .group(Group.of("Label", "form-hhot-field").noPaddingTop()
                    .controlHtml("<span class=\"pf-v6-c-form-control\">"
                            + "<input type=\"text\" id=\"form-hhot-field\" /></span>")
                    .helper("Helper text shown above the control.").helperOnTop().build())
            .build();

    public static Form demoFormStacked = Form.of("form-horizontal-stacked").horizontal()
            .group(Group.of("Label", "form-horizontal-stacked-opts").noPaddingTop()
                    .controlHtml("""
                            <div class="pf-v6-c-check">
                              <input class="pf-v6-c-check__input" type="checkbox" id="form-horizontal-stacked-opts" />
                              <label class="pf-v6-c-check__label" for="form-horizontal-stacked-opts">Option 1</label>
                            </div>
                            <div class="pf-v6-c-check">
                              <input class="pf-v6-c-check__input" type="checkbox" id="form-horizontal-stacked-opt2" />
                              <label class="pf-v6-c-check__label" for="form-horizontal-stacked-opt2">Option 2</label>
                            </div>""").build())
            .build();

    public static Form demoFormSections = Form.of("form-sections").style("max-width: 480px")
            .section("Section 1",
                    Group.of("Form section 1 input", "form-sections-f1")
                            .controlHtml("<span class=\"pf-v6-c-form-control\">"
                                    + "<input type=\"text\" id=\"form-sections-f1\" /></span>").build())
            .section("Section 2 (optional)",
                    Group.of("Form section 2 input", "form-sections-f2")
                            .controlHtml("<span class=\"pf-v6-c-form-control\">"
                                    + "<input type=\"text\" id=\"form-sections-f2\" /></span>").build())
            .build();

    public static Form demoFormInvalidAlert = Form.of("form-invalid-alert").style("max-width: 480px")
            .dangerAlert("Fill out all required fields before continuing.")
            .group(Group.of("Age", "form-invalid-alert-age").required()
                    .controlHtml("<span class=\"pf-v6-c-form-control pf-m-required pf-m-error\">"
                            + "<input type=\"text\" id=\"form-invalid-alert-age\" aria-invalid=\"true\""
                            + " value=\"Thirty\" /></span>")
                    .helper("Age must be a number.", "error").build())
            .build();
}
