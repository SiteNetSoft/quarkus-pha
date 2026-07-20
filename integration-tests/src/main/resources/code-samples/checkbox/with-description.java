import org.sitenetsoft.quarkus.pha.model.*;

Check check = Check.of("cb-desc").label("Email me weekly digests")
        .description("Includes activity summary and recommended reads. You can unsubscribe at any time.")
        .build();

// Template side, with the data in scope:
// {#include components/forms/check check=check /}
