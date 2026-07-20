import org.sitenetsoft.quarkus.pha.model.*;

TextInputGroup textInputGroup = TextInputGroup.of("tig-disabled")
        .placeholder("Disabled").disabled().build();

// Template side, with the data in scope:
// {#include components/forms/text-input-group textInputGroup=textInputGroup /}
