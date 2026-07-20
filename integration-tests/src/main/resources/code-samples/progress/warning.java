import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-warning", 66)
        .title("Disk usage high").variant("warning")
        .helperText("Approaching the configured 80% threshold.").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
