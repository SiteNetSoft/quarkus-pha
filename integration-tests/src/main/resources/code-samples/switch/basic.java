import org.sitenetsoft.quarkus.pha.model.*;

Switch sw = Switch.of("sw-basic").label("Enable notifications").build();

// Template side, with the data in scope:
// {#include components/forms/switch sw=sw /}
