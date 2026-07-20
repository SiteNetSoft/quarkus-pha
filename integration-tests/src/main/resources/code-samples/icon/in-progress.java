import org.sitenetsoft.quarkus.pha.model.*;

Icon iconInProgress = Icon.of("fa:save").size("xl").inProgress().label("Saving").build();

// Template side, with the data in scope:
// {#include components/icon iconModel=icon... /} (one include per icon)
