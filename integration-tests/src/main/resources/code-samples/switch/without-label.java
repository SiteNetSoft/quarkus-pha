import org.sitenetsoft.quarkus.pha.model.*;

Switch sw = Switch.of("sw-without-label")
        .ariaLabel("Enable feature").checked().build();

// Template side, with the data in scope:
// {#include components/forms/switch sw=sw /}
