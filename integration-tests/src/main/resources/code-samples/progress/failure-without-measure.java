import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-fail-no-measure", 42)
        .title("Index rebuild interrupted").variant("danger").noMeasure().build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
