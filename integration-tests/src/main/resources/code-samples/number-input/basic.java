import org.sitenetsoft.quarkus.pha.model.*;

NumberInput numberInput = NumberInput.of("ni-basic", 5).build();

// Template side, with the data in scope:
// {#include components/forms/number-input numberInput=numberInput /}
