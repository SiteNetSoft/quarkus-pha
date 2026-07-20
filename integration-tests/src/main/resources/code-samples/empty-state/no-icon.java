import org.sitenetsoft.quarkus.pha.model.*;

EmptyState emptyState = EmptyState.of("Nothing matches your search")
        .body("Try adjusting the filters or searching for different keywords.")
        .actionGroup(Button.of("Clear filters").variant("link").build())
        .build();

// Template side, with the data in scope:
// {#include components/feedback/empty-state emptyState=emptyState /}
