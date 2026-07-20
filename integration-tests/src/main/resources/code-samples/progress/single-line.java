import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-singleline", 33)
        .ariaLabel("Single-line progress").asSingleline().build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
