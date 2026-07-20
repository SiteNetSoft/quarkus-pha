import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-inside", 33)
        .title("Inside measure").measureInside().size("lg").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
