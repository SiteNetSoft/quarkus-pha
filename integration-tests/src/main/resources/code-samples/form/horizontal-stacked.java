import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Form.GridItem;
import org.sitenetsoft.quarkus.pha.model.Form.Group;

Form form = Form.of("form-horizontal-stacked").horizontal()
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

// Template side, with the data in scope:
// {#include components/forms/form form=form /}
