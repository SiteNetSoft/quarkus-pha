import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-no-measure", 33)
        .title("Without measure").noMeasure().build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
