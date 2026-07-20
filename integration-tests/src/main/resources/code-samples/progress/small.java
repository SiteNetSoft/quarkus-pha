import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-small", 33)
        .title("Small progress").size("sm").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
