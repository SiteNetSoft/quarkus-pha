import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-title-outside", 33)
        .title("Title outside progress").measureOutside().build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
