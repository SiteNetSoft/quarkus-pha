import org.sitenetsoft.quarkus.pha.model.*;

BackgroundImage backgroundImage = BackgroundImage.of("/web/images/pf-background.svg")
        .id("bg-image-basic").build();

// Template side, with the data in scope:
// {#include components/feedback/background-image backgroundImage=backgroundImage /}
