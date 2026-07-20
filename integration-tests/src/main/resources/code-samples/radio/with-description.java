import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Radio> items = List.of(
        Radio.of("r-desc-one", "r-desc").label("Standard")
                .description("3 GB storage, basic support.").checked().build(),
        Radio.of("r-desc-two", "r-desc").label("Pro")
                .description("30 GB storage, priority support.").build(),
        Radio.of("r-desc-three", "r-desc").label("Enterprise")
                .description("Unlimited storage, dedicated account manager.").build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/radio radio=x /}{/for}
