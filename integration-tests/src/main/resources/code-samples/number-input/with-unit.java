import org.sitenetsoft.quarkus.pha.model.*;

NumberInput numberInput = NumberInput.of("ni-unit", 24).min(0).max(72).unit("hours").build();

// Template side, with the data in scope:
// {#include components/forms/number-input numberInput=numberInput /}
