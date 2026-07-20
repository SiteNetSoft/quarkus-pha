import org.sitenetsoft.quarkus.pha.model.*;

Truncate truncate = Truncate.of("Vestibulum interdum risus et enim faucibus,"
        + " sit amet molestie est accumsan."
        + " redhat_logo_black_and_white_reversed_simple_with_fedora_container.zip")
        .id("trunc-link").link("#").build();

// Template side, with the data in scope:
// {#include components/data-display/truncate truncate=truncate /}
