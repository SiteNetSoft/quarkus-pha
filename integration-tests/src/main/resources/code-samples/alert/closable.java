import org.sitenetsoft.quarkus.pha.model.*;

Alert alert = Alert.of("Dismissable alert").id("al-close").variant("info")
        .asClosable().build();

// Template side, with the data in scope:
// {#include components/feedback/alert alert=alert /}
