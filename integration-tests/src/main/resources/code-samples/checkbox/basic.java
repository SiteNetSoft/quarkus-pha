import org.sitenetsoft.quarkus.pha.model.*;

Check check = Check.of("cb-basic").label("Accept terms").build();

// Template side, with the data in scope:
// {#include components/forms/check check=check /}
