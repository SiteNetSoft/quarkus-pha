import org.sitenetsoft.quarkus.pha.model.*;

Divider divider = Divider.builder()
        .insetOn("md", "sm").insetOn("lg", "md").insetOn("xl", "lg").insetOn("2xl", "xl").build();

// Template side, with the data in scope:
// {#include components/data-display/divider divider=divider /}
