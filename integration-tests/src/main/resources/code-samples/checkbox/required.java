import org.sitenetsoft.quarkus.pha.model.*;

Check check = Check.of("cb-required").label("I agree to the terms").required().build();

// Template side, with the data in scope:
// {#include components/forms/check check=check /}
