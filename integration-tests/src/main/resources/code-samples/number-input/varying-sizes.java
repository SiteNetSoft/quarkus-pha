import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<NumberInput> inputs = List.of(
        NumberInput.of("ni-size-1", 1).widthCh(4).build(),
        NumberInput.of("ni-size-2", 1234567).widthCh(10).build(),
        NumberInput.of("ni-size-3", 12).widthCh(18).build());

// Template side, with the data in scope:
// {#for n in inputs}{#include components/forms/number-input numberInput=n /}{/for}
