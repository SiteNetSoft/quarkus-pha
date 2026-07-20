import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.HelperText.Item;

HelperText helperText = HelperText.list("ht-multi-pwd")
        .item(Item.of("At least 8 characters").variant("success"))
        .item(Item.of("Contains an uppercase letter").variant("success"))
        .item(Item.of("Contains a number").variant("warning"))
        .item(Item.of("Contains a special character").variant("error"))
        .build();

// Template side, with the data in scope:
// {#include components/feedback/helper-text helperText=helperText /}
