import org.sitenetsoft.quarkus.pha.model.*;

EmptyState emptyState = EmptyState.of("Empty state").icon("fa:cubes")
        .body("This represents the empty state pattern in PatternFly. Hopefully it's simple"
                + " enough to use but flexible enough to meet a variety of needs.")
        .actionGroup(Button.of("Primary action").variant("primary").build())
        .actionGroup(Button.of("Multiple").variant("link").build(),
                Button.of("Action buttons").variant("link").build(),
                Button.of("Can").variant("link").build(),
                Button.of("Go here").variant("link").build(),
                Button.of("In the second").variant("link").build(),
                Button.of("Action area").variant("link").build())
        .build();

// Template side, with the data in scope:
// {#include components/feedback/empty-state emptyState=emptyState /}
