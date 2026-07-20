package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.EmptyState;

/**
 * Demo data for the empty-state examples — the examples on
 * /components/empty-state are populated from these models; footer actions are
 * composed Button models in ordered groups. Globals so the standalone example
 * route (which renders templates without data) can see them. Each is mirrored
 * by a snippet in resources/code-samples/empty-state/ served on the example
 * card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class EmptyStateDemoData {

    public static EmptyState demoEmptyBasic = EmptyState.of("Empty state").icon("fa:cubes")
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

    public static EmptyState demoEmptyXl = EmptyState.of("Empty state").size("xl").icon("fa:cubes")
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

    public static EmptyState demoEmptyXs = EmptyState.of("Empty state").size("xs").icon("fa:cubes")
            .body("This represents the empty state pattern in PatternFly. The icon is optional.")
            .actionGroup(Button.of("Multiple").variant("link").size("small").build(),
                    Button.of("Action buttons").variant("link").size("small").build(),
                    Button.of("Can").variant("link").size("small").build(),
                    Button.of("Go here").variant("link").size("small").build(),
                    Button.of("In the").variant("link").size("small").build(),
                    Button.of("Action area").variant("link").size("small").build())
            .build();

    public static EmptyState demoEmptyLg = EmptyState.of("Empty state").size("lg").icon("fa:cubes")
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

    public static EmptyState demoEmptyNoIcon = EmptyState.of("Nothing matches your search")
            .body("Try adjusting the filters or searching for different keywords.")
            .actionGroup(Button.of("Clear filters").variant("link").build())
            .build();

    public static EmptyState demoEmptyNoMatch = EmptyState.of("No results found")
            .icon("fa:magnifying-glass")
            .body("No results match the filter criteria. Clear all filters and try again.")
            .actionGroup(Button.of("Clear all filters").variant("link").build())
            .build();

    public static EmptyState demoEmptySm = EmptyState.of("Empty state").size("sm").icon("fa:cubes")
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

    public static EmptyState demoEmptySpinner = EmptyState.of("Loading").spinner("xl", "Loading").build();

    public static EmptyState demoEmptySuccess = EmptyState.of("You're all set")
            .status("success").icon("fa:circle-check")
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

    public static EmptyState demoEmptyActions = EmptyState.of("Empty workspace")
            .icon("fa:folder-open")
            .body("You haven't created any documents yet.")
            .actionGroup(Button.of("Create document").variant("primary").build(),
                    Button.of("Import existing").variant("secondary").build(),
                    Button.of("Browse templates").variant("link").build(),
                    Button.of("Learn more").variant("link").build())
            .build();
}
