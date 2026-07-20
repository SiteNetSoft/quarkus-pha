import org.sitenetsoft.quarkus.pha.model.*;

SkipToContent skipToContent = SkipToContent.of("#main-content", "Jump to main content")
        .id("stc-custom").build();

// Template side, with the data in scope:
// {#include components/navigation/skip-to-content skipToContent=skipToContent /}
