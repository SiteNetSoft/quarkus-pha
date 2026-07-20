import org.sitenetsoft.quarkus.pha.model.*;

FormSelect formSelect = FormSelect.of("fs-error").validated("error")
        .options("""
                <option value="">Must select an option</option>
                <option value="a">A</option>
                <option value="b">B</option>""")
        .build();

// Template side, with the data in scope:
// {#include components/forms/form-select formSelect=formSelect /}
