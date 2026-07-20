import org.sitenetsoft.quarkus.pha.model.*;

Radio radio = Radio.of("rd-label-wraps", "rd-label-wraps")
        .label("A really long radio label that wraps onto several lines while the input"
                + " stays aligned to the first line").build();

// Template side, with the data in scope:
// {#include components/forms/radio radio=radio /}
