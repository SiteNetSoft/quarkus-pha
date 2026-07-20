import org.sitenetsoft.quarkus.pha.model.*;

InlineEdit inlineEdit = InlineEdit.multiple("ie-multiple").style("max-width: 32rem")
        .field("Name", "Jane Smith").field("Team", "Platform").field("Role", "Editor").build();

// Template side, with the data in scope:
// {#include components/forms/inline-edit inlineEdit=inlineEdit /}
