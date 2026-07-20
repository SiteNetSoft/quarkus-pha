import org.sitenetsoft.quarkus.pha.model.*;

EmptyState emptyState = EmptyState.of("Empty workspace")
        .icon("fa:folder-open")
        .body("You haven't created any documents yet.")
        .actionGroup(Button.of("Create document").variant("primary").build(),
                Button.of("Import existing").variant("secondary").build(),
                Button.of("Browse templates").variant("link").build(),
                Button.of("Learn more").variant("link").build())
        .build();

// Template side, with the data in scope:
// {#include components/feedback/empty-state emptyState=emptyState /}
