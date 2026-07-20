import org.sitenetsoft.quarkus.pha.model.*;

FormSelect formSelect = FormSelect.of("fs-grouped")
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

// Template side, with the data in scope:
// {#include components/forms/form-select formSelect=formSelect /}
