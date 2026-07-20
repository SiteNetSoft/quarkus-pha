import org.sitenetsoft.quarkus.pha.model.*;

Switch sw = Switch.of("sw-checked").label("Already on").checked().build();

// Template side, with the data in scope:
// {#include components/forms/switch sw=sw /}
