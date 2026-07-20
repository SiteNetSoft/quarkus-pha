import org.sitenetsoft.quarkus.pha.model.*;

Icon iconDlProgress = Icon.of("fa:download").size("xl")
        .inProgress().progressIcon("fa:hourglass-half").label("Downloading").build();

Icon iconUlProgress = Icon.of("fa:upload").size("xl")
        .inProgress().label("Uploading").build();

// Template side, with the data in scope:
// {#include components/icon iconModel=icon... /} (one include per icon)
