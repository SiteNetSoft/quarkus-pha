import org.sitenetsoft.quarkus.pha.model.*;

TextInputGroup textInputGroup = TextInputGroup.of("tig-plain").plain()
        .value("Text input group with plain styling").ariaLabel("Plain text input group").build();

// Template side, with the data in scope:
// {#include components/forms/text-input-group textInputGroup=textInputGroup /}
