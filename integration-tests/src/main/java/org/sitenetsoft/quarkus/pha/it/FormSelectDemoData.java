package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.FormSelect;

/**
 * Demo data for the form-select examples — the examples on
 * /components/form-select are populated from these models. Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/form-select/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class FormSelectDemoData {

    public static FormSelect demoFsBasic = FormSelect.of("fs-basic")
            .options("""
                    <option value="">Select…</option>
                    <option value="mr">Mr</option>
                    <option value="mrs">Mrs</option>
                    <option value="ms">Ms</option>
                    <option value="dr">Dr</option>""")
            .build();

    public static FormSelect demoFsDisabled = FormSelect.of("fs-disabled").disabled()
            .options("""
                    <option value="a">A</option>
                    <option value="b">B</option>""")
            .build();

    public static FormSelect demoFsGrouped = FormSelect.of("fs-grouped")
            .options("""
                    <option value="">Pick a city…</option>
                    <optgroup label="North America">
                      <option value="ny">New York</option>
                      <option value="tor">Toronto</option>
                      <option value="mex">Mexico City</option>
                    </optgroup>
                    <optgroup label="Europe">
                      <option value="lon">London</option>
                      <option value="par">Paris</option>
                      <option value="ber">Berlin</option>
                    </optgroup>""")
            .build();

    public static FormSelect demoFsError = FormSelect.of("fs-error").validated("error")
            .options("""
                    <option value="">Must select an option</option>
                    <option value="a">A</option>
                    <option value="b">B</option>""")
            .build();
}
