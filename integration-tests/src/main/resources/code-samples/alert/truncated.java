import org.sitenetsoft.quarkus.pha.model.*;

Alert alert = Alert
        .of("Really long info alert title that will be truncated to a single line by the pf-m-truncate title modifier no matter how much text keeps going and going and going")
        .id("al-truncated").variant("info").asTruncated().build();

// Template side, with the data in scope:
// {#include components/feedback/alert alert=alert /}
