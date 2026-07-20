import org.sitenetsoft.quarkus.pha.model.*;

Banner banner = Banner.of("This banner has screen reader text.")
        .id("banner-sr").variant("info").screenReaderText("Info alert:").build();

// Template side, with the data in scope:
// {#include components/feedback/banner banner=banner /}
