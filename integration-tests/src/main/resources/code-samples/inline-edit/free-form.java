import org.sitenetsoft.quarkus.pha.model.*;

InlineEdit inlineEdit = InlineEdit.freeForm("ie-free-form",
        "Free form text — click and type to edit.", "Editable free form text").build();

// Template side, with the data in scope:
// {#include components/forms/inline-edit inlineEdit=inlineEdit /}
