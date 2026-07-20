import org.sitenetsoft.quarkus.pha.model.*;

NumberInput numberInput = NumberInput.of("ni-custom-step", 90).step(3).build();

// Template side, with the data in scope:
// {#include components/forms/number-input numberInput=numberInput /}
