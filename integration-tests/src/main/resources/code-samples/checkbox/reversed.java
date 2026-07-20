import org.sitenetsoft.quarkus.pha.model.*;

Check check = Check.of("cb-reversed")
        .label("Label first, then the input").reversed().build();

// Template side, with the data in scope:
// {#include components/forms/check check=check /}
