import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-helper-text", 33)
        .title("Progress with helper text")
        .helperText("Disk usage projected to peak at 78% before the next nightly cleanup.").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
