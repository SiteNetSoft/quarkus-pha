import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-outside", 33)
        .title("Outside measure").measureOutside().build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
