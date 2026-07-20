import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-outside-failure", 42)
        .title("Sync failed").variant("danger").measureOutside().build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
