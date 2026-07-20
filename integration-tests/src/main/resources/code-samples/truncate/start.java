import org.sitenetsoft.quarkus.pha.model.*;

Truncate truncate = Truncate.of("/home/user/projects/very/deep/nested/path/to/a/file.txt")
        .id("trunc-start").position("start").build();

// Template side, with the data in scope:
// {#include components/data-display/truncate truncate=truncate /}
