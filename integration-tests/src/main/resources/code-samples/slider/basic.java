import org.sitenetsoft.quarkus.pha.model.*;

Slider slider = Slider.of("sl-basic", 50).build();

// Template side, with the data in scope:
// {#include components/forms/slider slider=slider /}
