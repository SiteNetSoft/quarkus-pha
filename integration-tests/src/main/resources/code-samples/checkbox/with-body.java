import org.sitenetsoft.quarkus.pha.model.*;

Check check = Check.of("cb-body").label("Customize delivery")
        .body("Choose hours and days for digest delivery (configurable after enabling).").build();

// Template side, with the data in scope:
// {#include components/forms/check check=check /}
