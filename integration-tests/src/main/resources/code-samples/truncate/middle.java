import org.sitenetsoft.quarkus.pha.model.*;

Truncate truncate = Truncate.of("abcdefghijklmnopqrstuvwxyz1234567890")
        .id("trunc-middle").position("middle").trailingNumChars(8).build();

// Template side, with the data in scope:
// {#include components/data-display/truncate truncate=truncate /}
