import org.sitenetsoft.quarkus.pha.model.*;

Divider divider = Divider.builder().vertical().inset("sm").build();

// Template side, with the data in scope:
// {#include components/data-display/divider divider=divider /}
