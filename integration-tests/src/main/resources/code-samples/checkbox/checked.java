import org.sitenetsoft.quarkus.pha.model.*;

Check check = Check.of("cb-checked").label("Already checked").checked().build();

// Template side, with the data in scope:
// {#include components/forms/check check=check /}
