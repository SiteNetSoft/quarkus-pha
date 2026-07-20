import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-large", 33)
        .title("Large progress").size("lg").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
