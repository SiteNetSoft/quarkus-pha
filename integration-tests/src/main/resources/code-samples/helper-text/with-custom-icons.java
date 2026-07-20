import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.HelperText.Item;

import java.util.List;

List<HelperText> helperTexts = List.of(
        HelperText.of("ht-icon-info", "Pro tip: use a strong password.")
                .variant("success").icon("fa:lightbulb").build(),
        HelperText.of("ht-icon-warning", "Your session will expire soon.")
                .variant("warning").icon("fa:clock").build(),
        HelperText.of("ht-icon-error", "Account temporarily locked.")
                .variant("error").icon("fa:lock").build());

// Template side, with the data in scope:
// {#for h in helperTexts}{#include components/feedback/helper-text helperText=h /}{/for}
