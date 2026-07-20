import org.sitenetsoft.quarkus.pha.model.*;

Truncate truncate = Truncate.of("This is a long string of text that will be"
        + " truncated at the end of the line when the container is narrow.")
        .id("trunc-end").build();

// Template side, with the data in scope:
// {#include components/data-display/truncate truncate=truncate /}
