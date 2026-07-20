import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<TextArea> items = List.of(
        TextArea.of("ta-disabled").rows(3).value("Disabled content").disabled().build(),
        TextArea.of("ta-readonly").rows(3).value("Read-only content").readonly().build());

// Template side, with the data in scope:
// {#for x in items}{#include components/forms/text-area textArea=x /}{/for}
