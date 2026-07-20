import org.sitenetsoft.quarkus.pha.model.*;

NumberInput numberInput = NumberInput.of("ni-custom-step-threshold", 6)
        .step(3).min(0).max(12).build();

// Template side, with the data in scope:
// {#include components/forms/number-input numberInput=numberInput /}
