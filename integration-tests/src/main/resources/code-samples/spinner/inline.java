import org.sitenetsoft.quarkus.pha.model.*;

Spinner spinner = Spinner.of("Loading").inline().build();

// Template side, with the data in scope:
// {#include components/feedback/spinner spinner=spinner /}
