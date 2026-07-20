import org.sitenetsoft.quarkus.pha.model.*;

BackToTop backToTop = BackToTop.of().id("back-to-top-basic").build();

// Template side, with the data in scope:
// {#include components/navigation/back-to-top backToTop=backToTop /}
