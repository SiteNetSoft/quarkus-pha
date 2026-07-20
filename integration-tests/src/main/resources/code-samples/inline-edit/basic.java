import org.sitenetsoft.quarkus.pha.model.*;

InlineEdit inlineEdit = InlineEdit.of("ie-basic", "Click the pencil to edit me").build();

// Template side, with the data in scope:
// {#include components/forms/inline-edit inlineEdit=inlineEdit /}
