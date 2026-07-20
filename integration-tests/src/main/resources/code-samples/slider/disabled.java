import org.sitenetsoft.quarkus.pha.model.*;

Slider slider = Slider.of("sl-disabled", 30).disabled().build();

// Template side, with the data in scope:
// {#include components/forms/slider slider=slider /}
