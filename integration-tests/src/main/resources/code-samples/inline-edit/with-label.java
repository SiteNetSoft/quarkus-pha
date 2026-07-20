import org.sitenetsoft.quarkus.pha.model.*;

InlineEdit inlineEdit = InlineEdit.labeled("ie-label", "Title",
        "Senior Engineer", "Edit title").style("max-width: 28rem").build();

// Template side, with the data in scope:
// {#include components/forms/inline-edit inlineEdit=inlineEdit /}
