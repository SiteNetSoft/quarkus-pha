import org.sitenetsoft.quarkus.pha.model.*;

FormSelect formSelect = FormSelect.of("fs-disabled").disabled()
        .options("""
                <option value="a">A</option>
                <option value="b">B</option>""")
        .build();

// Template side, with the data in scope:
// {#include components/forms/form-select formSelect=formSelect /}
