import org.sitenetsoft.quarkus.pha.model.*;

InlineEdit inlineEdit = InlineEdit.validated("ie-validated", "Display name",
        "Type to validate…", "Display name is required.").style("max-width: 28rem").build();

// Template side, with the data in scope:
// {#include components/forms/inline-edit inlineEdit=inlineEdit /}
