import org.sitenetsoft.quarkus.pha.model.*;

Skeleton skeleton = Skeleton.builder()
        .id("sk-default").screenReaderText("Loading").build();

// Template side, with the data in scope:
// {#include components/feedback/skeleton skeleton=skeleton /}
