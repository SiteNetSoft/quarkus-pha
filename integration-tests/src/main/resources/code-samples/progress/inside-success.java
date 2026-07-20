import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-inside-success", 100)
        .title("Backup complete").variant("success").measureInside().size("lg").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
