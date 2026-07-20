import org.sitenetsoft.quarkus.pha.model.*;

Divider divider = Divider.builder().vertical()
        .insetOn("md", "xs").insetOn("lg", "sm").insetOn("xl", "md").build();

// Template side, with the data in scope:
// {#include components/data-display/divider divider=divider /}
