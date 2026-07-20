import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.HelperText.Item;

import java.util.List;

List<HelperText> helperTexts = List.of(
        HelperText.of("ht-basic-static", "This is default helper text").build(),
        HelperText.of("ht-basic-indeterminate", "This is indeterminate helper text")
                .variant("indeterminate").build(),
        HelperText.of("ht-basic-warning", "This is warning helper text").variant("warning").build(),
        HelperText.of("ht-basic-success", "This is success helper text").variant("success").build(),
        HelperText.of("ht-basic-error", "This is error helper text").variant("error").build());

// Template side, with the data in scope:
// {#for h in helperTexts}{#include components/feedback/helper-text helperText=h /}{/for}
