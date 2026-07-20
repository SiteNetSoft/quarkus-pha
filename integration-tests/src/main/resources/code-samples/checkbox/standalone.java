import org.sitenetsoft.quarkus.pha.model.*;

Check check = Check.standalone("cb-standalone", "Select all rows").build();

// Template side, with the data in scope:
// {#include components/forms/check check=check /}
