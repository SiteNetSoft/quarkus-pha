import org.sitenetsoft.quarkus.pha.model.*;

Slider slider = Slider.of("sl-temp", 20).min(-10).max(40).build();

// Template side, with the data in scope:
// {#include components/forms/slider slider=slider /}
