import org.sitenetsoft.quarkus.pha.model.*;

Switch sw = Switch.of("sw-rev")
        .label("Label first, then the toggle").reversed().build();

// Template side, with the data in scope:
// {#include components/forms/switch sw=sw /}
