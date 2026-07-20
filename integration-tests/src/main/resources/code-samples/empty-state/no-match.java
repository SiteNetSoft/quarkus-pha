import org.sitenetsoft.quarkus.pha.model.*;

EmptyState emptyState = EmptyState.of("No results found")
        .icon("fa:magnifying-glass")
        .body("No results match the filter criteria. Clear all filters and try again.")
        .actionGroup(Button.of("Clear all filters").variant("link").build())
        .build();

// Template side, with the data in scope:
// {#include components/feedback/empty-state emptyState=emptyState /}
