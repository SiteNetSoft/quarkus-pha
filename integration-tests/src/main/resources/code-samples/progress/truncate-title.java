import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-truncate", 42)
        .title("A really really long progress title that will run out of space and need to be truncated with an ellipsis")
        .asTruncatedTitle().build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
