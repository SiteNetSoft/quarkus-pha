import org.sitenetsoft.quarkus.pha.model.*;

EmptyState emptyState = EmptyState.of("Loading").spinner("xl", "Loading").build();

// Template side, with the data in scope:
// {#include components/feedback/empty-state emptyState=emptyState /}
