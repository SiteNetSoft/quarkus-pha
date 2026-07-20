import org.sitenetsoft.quarkus.pha.model.*;

SkipToContent skipToContent = SkipToContent.of("#main-content", "Skip to content")
        .id("stc-basic").build();

// Template side, with the data in scope:
// {#include components/navigation/skip-to-content skipToContent=skipToContent /}
