import org.sitenetsoft.quarkus.pha.model.*;

Radio radio = Radio.standalone("r-standalone", "r-standalone").build();

// Template side, with the data in scope:
// {#include components/forms/radio radio=radio /}
