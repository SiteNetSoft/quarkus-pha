import org.sitenetsoft.quarkus.pha.model.*;

FormSelect formSelect = FormSelect.of("fs-basic")
        .options("""
                <option value="">Select…</option>
                <option value="mr">Mr</option>
                <option value="mrs">Mrs</option>
                <option value="ms">Ms</option>
                <option value="dr">Dr</option>""")
        .build();

// Template side, with the data in scope:
// {#include components/forms/form-select formSelect=formSelect /}
