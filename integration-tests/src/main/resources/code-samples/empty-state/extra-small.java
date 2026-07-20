import org.sitenetsoft.quarkus.pha.model.*;

EmptyState emptyState = EmptyState.of("Empty state").size("xs").icon("fa:cubes")
        .body("This represents the empty state pattern in PatternFly. The icon is optional.")
        .actionGroup(Button.of("Multiple").variant("link").size("small").build(),
                Button.of("Action buttons").variant("link").size("small").build(),
                Button.of("Can").variant("link").size("small").build(),
                Button.of("Go here").variant("link").size("small").build(),
                Button.of("In the").variant("link").size("small").build(),
                Button.of("Action area").variant("link").size("small").build())
        .build();

// Template side, with the data in scope:
// {#include components/feedback/empty-state emptyState=emptyState /}
