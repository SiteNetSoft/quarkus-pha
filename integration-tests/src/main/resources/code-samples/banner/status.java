import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Banner> banners = List.of(
        Banner.of("Success banner message").id("banner-success").variant("success")
                .screenReaderText("Success:").build(),
        Banner.of("Warning banner message").id("banner-warning").variant("warning")
                .screenReaderText("Warning:").build(),
        Banner.of("Danger banner message").id("banner-danger").variant("danger")
                .screenReaderText("Danger:").build(),
        Banner.of("Info banner message").id("banner-info").variant("info")
                .screenReaderText("Info:").build(),
        Banner.of("Custom banner message").id("banner-custom").variant("custom").build());

// Template side, with the data in scope:
// {#for b in banners}{#include components/feedback/banner banner=b /}{/for}
