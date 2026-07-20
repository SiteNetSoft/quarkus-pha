import org.sitenetsoft.quarkus.pha.model.*;

NumberInput numberInput = NumberInput.of("ni-bounded", 3).min(0).max(10).build();

// Template side, with the data in scope:
// {#include components/forms/number-input numberInput=numberInput /}
