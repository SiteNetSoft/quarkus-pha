import org.sitenetsoft.quarkus.pha.model.*;

Radio radio = Radio.of("rd-desc-body", "rd-desc-body")
        .label("With description and body")
        .description("A longer explanation of what selecting this option means.")
        .body("Extra body content - links or fine print can live here.").build();

// Template side, with the data in scope:
// {#include components/forms/radio radio=radio /}
