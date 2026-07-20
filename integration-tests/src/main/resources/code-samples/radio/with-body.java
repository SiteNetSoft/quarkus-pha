import org.sitenetsoft.quarkus.pha.model.*;

Radio radio = Radio.of("r-body", "r-body").label("Custom plan")
        .body("Choose features individually after picking this option.").checked().build();

// Template side, with the data in scope:
// {#include components/forms/radio radio=radio /}
