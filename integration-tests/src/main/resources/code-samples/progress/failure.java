import org.sitenetsoft.quarkus.pha.model.*;

Progress progress = Progress.of("prog-failure", 33)
        .title("Backup failed").variant("danger")
        .helperText("Run failed at 33% — see the audit log for details.").build();

// Template side, with `progress` in the template data:
// {#include components/feedback/progress progress=progress /}
