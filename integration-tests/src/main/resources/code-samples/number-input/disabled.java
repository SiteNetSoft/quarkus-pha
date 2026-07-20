import org.sitenetsoft.quarkus.pha.model.*;

NumberInput numberInput = NumberInput.of("ni-disabled", 5).disabled().build();

// Template side, with the data in scope:
// {#include components/forms/number-input numberInput=numberInput /}
