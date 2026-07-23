import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-static-width", 33)
        .title("Static width measure")
        .staticWidthMeasure()
        .build();

// Template side, with the data in scope:
// {#include components/feedback/progress progress=progress /}
