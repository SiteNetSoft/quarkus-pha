import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-success", 100)
        .title("Backup complete").variant("success").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
