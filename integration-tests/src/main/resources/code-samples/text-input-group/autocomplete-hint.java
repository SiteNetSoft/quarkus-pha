import org.sitenetsoft.quarkus.pha.model.*;

TextInputGroup textInputGroup = TextInputGroup.of("tig-hint")
        .value("apples").hint("appleseed").ariaLabel("Autocomplete with last option hint").build();

// Template side, with the data in scope:
// {#include components/forms/text-input-group textInputGroup=textInputGroup /}
