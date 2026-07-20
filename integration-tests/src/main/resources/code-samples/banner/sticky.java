import org.sitenetsoft.quarkus.pha.model.*;

Banner banner = Banner.of("Sticky warning banner")
        .id("banner-sticky").variant("warning").asSticky().screenReaderText("Warning:").build();

// Template side, with the data in scope:
// {#include components/feedback/banner banner=banner /}
